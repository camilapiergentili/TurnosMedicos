package ar.com.dontar.demo.service.implementation;

import ar.com.dontar.demo.controller.dto.AppointmentDto;
import ar.com.dontar.demo.controller.response.AppointmentResponse;
import ar.com.dontar.demo.controller.response.SpecialityResponse;
import ar.com.dontar.demo.exception.*;
import ar.com.dontar.demo.mapper.MapperAppointment;
import ar.com.dontar.demo.model.Appointment;
import ar.com.dontar.demo.model.AppointmentStatus;
import ar.com.dontar.demo.persistence.AppointmentRepository;
import ar.com.dontar.demo.persistence.entity.*;
import ar.com.dontar.demo.service.AppointmentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    ProfessionalServiceImpl professionalService;

    @Autowired
    PatientServiceImpl patientService;

    @Autowired
    SpecialityServiceImpl specialityService;

    @Override
    @Transactional
    public void appointmentGenerate(long idProfessional) throws UserNotExistsException, ScheduleNotExistsException, AppoinmentNotGenerateException {

        ProfessionalEntity professionalEntity = professionalService.getProfessionalWithScheduleEntity(idProfessional);

        if (professionalEntity.getScheduleEntity().isEmpty()) {
            throw new ScheduleNotExistsException("El profesional " + professionalEntity.getLastName() + " no tiene agenda disponible.");
        }

        List<Appointment> appointments = genetateAppointmentForMonth(professionalEntity, LocalDate.now());


        if (appointments.isEmpty()) {
            throw new AppoinmentNotGenerateException("No se pudieron generar turnos para el profesional.");
        }

        List<AppointmentEntity> appointmentEntities = appointments.stream()
                .map(appointment -> {
                    AppointmentEntity entity = MapperAppointment.appointmentModelToEntity(appointment);
                    entity.setProfessional(professionalEntity);
                    return entity;
                }).toList();

        professionalEntity.setAppointmentsProfessional(appointmentEntities);

        try {
            appointmentRepository.saveAll(appointmentEntities);
        } catch (Exception e) {

            throw new AppoinmentNotGenerateException("Error al guardar los turnos en la base de datos.");
        }
    }

    private List<Appointment> genetateAppointmentForMonth(ProfessionalEntity professionalEntity, LocalDate startAppointment) throws AppoinmentNotGenerateException {

        List<Appointment> appointments = new ArrayList<>();
        LocalDate endAppointment = startAppointment.plusMonths(1);

        while (!startAppointment.isAfter(endAppointment)) {
            appointments.addAll(generateAppointmentForDay(professionalEntity, startAppointment));
            startAppointment = startAppointment.plusDays(1);
        }

        return appointments;

    }

    private List<Appointment> generateAppointmentForDay(ProfessionalEntity professionalEntity, LocalDate startAppointment) throws AppoinmentNotGenerateException {
        List<Appointment> appointments = new ArrayList<>();
        for (ScheduleEntity s : professionalEntity.getScheduleEntity()) {
            if (startAppointment.getDayOfWeek().equals(s.getDay())) {
                LocalTime timeAppointment = s.getStartTime();

                while (timeAppointment.isBefore(s.getEndTime())) {
                    boolean exists = appointmentRepository.existsByProfessionalAndAppointmentDayAndAppointmentTime(
                            professionalEntity, startAppointment, timeAppointment);

                    if(!exists){
                        Appointment appointment = new Appointment();
                        appointment.setAppointmentDay(startAppointment);
                        appointment.setAppointmentTime(timeAppointment);
                        appointment.setAppointmentStatus(AppointmentStatus.DISPONIBLE);
                        appointments.add(appointment);

                    }

                    timeAppointment = timeAppointment.plusMinutes(15);
                }
            }
        }

        return appointments;

    }

    @Override
    @Transactional
    public List<LocalTime> getAvailableAppointment(long idProfessional, String date) throws UserNotExistsException, AppointmentNotExistsException, AppoinmentNotGenerateException {

        professionalService.findProfessionalEntityById(idProfessional);

        LocalDate dateToLocalDate = LocalDate.parse(date);

        if(dateToLocalDate.isBefore(LocalDate.now())){
            throw new AppoinmentNotGenerateException("La fecha ya paso");
        }

        List<LocalTime> availableTime = appointmentRepository.getAvailableAppointmentTimesForProfessional(idProfessional, dateToLocalDate);

        if(availableTime.isEmpty()){
            throw new AppointmentNotExistsException("No hay turnos disponibles para " + dateToLocalDate);
        }

        return availableTime;
    }

    @Override
    public List<LocalTime> getUnavailableAppointment(long idProfessional, String date) throws UserNotExistsException, AppoinmentNotGenerateException {

        professionalService.findProfessionalEntityById(idProfessional);

        LocalDate dateAppointment = LocalDate.parse(date);

        if(dateAppointment.isBefore(LocalDate.now())){
            throw new AppoinmentNotGenerateException("La fecha ya paso");
        }

        return appointmentRepository.getUnavailableAppointmentTimesForProfessional(idProfessional, dateAppointment);
    }

    @Override
    @Transactional
    public void bookAppointment(AppointmentDto appointmentDto) throws UserNotExistsException, AppointmentNotExistsException, AppoinmentNotGenerateException, SpecialityNotExistsException {

        ProfessionalEntity professionalEntity = professionalService.findProfessionalEntityById(appointmentDto.getIdProfessional());
        PatientEntity patientEntity = patientService.findPatientEntityById(appointmentDto.getIdPatient());
        SpecialityEntity specialityEntity = specialityService.findSpecialityEntity(appointmentDto.getIdSpeciality());

        if (!professionalEntity.getSpecialities().contains(specialityEntity)) {
            throw new SpecialityNotExistsException("La especialidad " + specialityEntity.getNameSpeciality()
                    + " no la atiende el profesional " + professionalEntity.getLastName());
        }

        Appointment appointment = MapperAppointment.appointmentDtoToModel(appointmentDto);

        if (appointment.getAppointmentDay().isBefore(LocalDate.now()) ||
                (appointment.getAppointmentDay().equals(LocalDate.now()) && appointment.getAppointmentTime().isBefore(LocalTime.now()))) {
            throw new AppoinmentNotGenerateException("El turno es pasado");
        }

        List<AppointmentEntity> availableAppointment = appointmentRepository.findAvailableAppointmentByProfessional(professionalEntity.getIdUser());

        if(availableAppointment.isEmpty()){
            throw new AppointmentNotExistsException("No hay turnos disponible con el profesional " + professionalEntity.getLastName()
                    + " para el dia " + appointment.getAppointmentDay() + " a las " + appointment.getAppointmentTime());
        }

        if(appointmentDto.isFirstAppointment()){
            appointmentFirstTime(availableAppointment, appointment, patientEntity, specialityEntity);
        } else {
            appointmentNormalReservation(availableAppointment, appointment, patientEntity, specialityEntity);
        }

    }

    private void appointmentNormalReservation(
            List<AppointmentEntity> availableAppointment,
            Appointment appointment,
            PatientEntity patientEntity,
            SpecialityEntity specialityEntity
    ) throws AppointmentNotExistsException {

        Optional<AppointmentEntity> normalReservation = availableAppointment.stream().
                filter(app -> app.getAppointmentDay().equals(appointment.getAppointmentDay()) &&
                        app.getAppointmentTime().equals(appointment.getAppointmentTime()))
                .findFirst();

        if (normalReservation.isEmpty()) {
            throw new AppointmentNotExistsException("No se encontró turno disponible para el día " +
                    appointment.getAppointmentDay() + " en el horario " + appointment.getAppointmentTime());
        }

        AppointmentEntity appointmentEntity = normalReservation.get();

        saveAppointment(appointmentEntity, patientEntity, specialityEntity);

        appointmentRepository.save(appointmentEntity);
    }

    private void appointmentFirstTime(
            List<AppointmentEntity> availableAppointment,
            Appointment appointment,
            PatientEntity patientEntity,
            SpecialityEntity specialityEntity
    ) throws AppointmentNotExistsException, AppoinmentNotGenerateException {

        Optional<AppointmentEntity> firstReservation = availableAppointment.stream()
                .filter(app -> app.getAppointmentDay().equals(appointment.getAppointmentDay()) &&
                        app.getAppointmentTime().equals(appointment.getAppointmentTime()))
                .findFirst();

        LocalTime timeSecond = appointment.getAppointmentTime().plusMinutes(15);

        Optional<AppointmentEntity> secondReservation = availableAppointment.stream()
                .filter(app -> app.getAppointmentDay().equals(appointment.getAppointmentDay()) &&
                        app.getAppointmentTime().equals(timeSecond))
                .findFirst();

        if (firstReservation.isEmpty() || secondReservation.isEmpty()) {
            throw new AppoinmentNotGenerateException("Este turno no está disponible para primera consulta");
        }

        List<AppointmentEntity> appointmentsToSave = new ArrayList<>();

        AppointmentEntity appointmentEntity = firstReservation.orElseThrow(() -> new AppointmentNotExistsException("No se encontro turno disponible para el día " +
                    appointment.getAppointmentDay() + " en el horario " + appointment.getAppointmentTime()));

        AppointmentEntity appointmentEntity2 = secondReservation.orElseThrow(() -> new AppointmentNotExistsException("No se encontro turno disponible para el día " +
                    appointment.getAppointmentDay() + " en el horario " + appointment.getAppointmentTime()));


        saveAppointment(appointmentEntity, patientEntity, specialityEntity);
        saveAppointment(appointmentEntity2, patientEntity, specialityEntity);

        appointmentsToSave.add(appointmentEntity);
        appointmentsToSave.add(appointmentEntity2);

        appointmentRepository.saveAll(appointmentsToSave);
    }

    private void saveAppointment(AppointmentEntity appointmentEntity, PatientEntity patientEntity, SpecialityEntity specialityEntity){
        appointmentEntity.setPatient(patientEntity);
        appointmentEntity.setSpeciality(specialityEntity);
        appointmentEntity.setAppointmentStatus(AppointmentStatus.RESERVADO);
    }

    @Override
    public void cancelAppointment(long idPatient, long idAppointment) throws UserNotExistsException, AppointmentNotExistsException, CancellationTimeExceededException {

        patientService.findPatientEntityById(idPatient);
        AppointmentEntity appointmentEntity = appointmentRepository.findById(idAppointment)
                .orElseThrow(() -> new AppointmentNotExistsException("El paciente no tiene turno asignado"));

        boolean cancel = isBefore48hours(appointmentEntity.getAppointmentDay(), appointmentEntity.getAppointmentTime());

        if(cancel) {
            throw new CancellationTimeExceededException("El turno excede las 48 horas para ser cancelado");
        }

        LocalTime nextTime = appointmentEntity.getAppointmentTime().plusMinutes(15);

        Optional<AppointmentEntity> nextAppointment = appointmentRepository.findNextAppointmentPatient(
                appointmentEntity.getProfessional().getIdUser(),
                appointmentEntity.getAppointmentDay(),
                nextTime,
                idPatient);

        if(nextAppointment.isPresent()){
            AppointmentEntity appointment = nextAppointment.get();
            appointment.setPatient(null);
            appointment.setSpeciality(null);
            appointment.setAppointmentStatus(AppointmentStatus.DISPONIBLE);
            appointmentRepository.save(appointment);
        }

        appointmentEntity.setPatient(null);
        appointmentEntity.setSpeciality(null);
        appointmentEntity.setAppointmentStatus(AppointmentStatus.DISPONIBLE);

        appointmentRepository.save(appointmentEntity);

    }

    private boolean isBefore48hours(LocalDate appointmentDay, LocalTime appointmentTime){
        LocalDateTime appointmentDateTime = LocalDateTime.of(appointmentDay, appointmentTime);
        LocalDateTime currentDate = LocalDateTime.now();

        long hoursDifference = ChronoUnit.HOURS.between(currentDate, appointmentDateTime);

        return hoursDifference < 48;

    }

    @Override
    public List<AppointmentResponse> getAppointmentsByPatient(long idPatient) throws UserNotExistsException {
        PatientEntity patientEntity = patientService.findPatientWithAppointment(idPatient);

        return patientEntity.getAppointmentsPatient().stream().map(appointment -> {
            AppointmentResponse response = new AppointmentResponse();
            response.setDayAppointment(appointment.getAppointmentDay());
            response.setTimeAppointment(appointment.getAppointmentTime());

            response.setNameProfessional(String.format("%s %s",
                    appointment.getProfessional().getFirstName(),
                    appointment.getProfessional().getLastName()));

            SpecialityEntity specialityEntity = appointment.getSpeciality();

            if (specialityEntity != null) {
                SpecialityResponse specialityResponse = new SpecialityResponse();
                specialityResponse.setName(specialityEntity.getNameSpeciality());
                response.setSpeciality(specialityResponse);
            }

            if (appointment.getAppointmentDay().isBefore(LocalDate.now()) ||
                    (appointment.getAppointmentDay().equals(LocalDate.now()) && appointment.getAppointmentTime().isBefore(LocalTime.now()))){
                response.setStatus(AppointmentStatus.FINALIZADO);
            }else {
                response.setStatus(AppointmentStatus.RESERVADO);
            }

            return response;
        }).collect(Collectors.toList());
    }


    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void updateExpiredAppointments() {
        appointmentRepository.markPastAppointmentsAsExpired();
    }

}
