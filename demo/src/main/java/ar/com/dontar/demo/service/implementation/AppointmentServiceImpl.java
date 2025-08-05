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


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
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

        if (professionalEntity.getScheduleEntity() == null || professionalEntity.getScheduleEntity().isEmpty()) {
            throw new ScheduleNotExistsException("El profesional " + professionalEntity.getLastName() + " no tiene agenda disponible.");
        }

        List<Appointment> appointments = generateAppointmentForMonth(professionalEntity, LocalDate.now());


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

    private List<Appointment> generateAppointmentForMonth(ProfessionalEntity professionalEntity, LocalDate startAppointment) throws AppoinmentNotGenerateException {

        List<Appointment> appointments = new ArrayList<>();
        LocalDate endAppointment = startAppointment.plusMonths(1);

        while (startAppointment.isBefore(endAppointment)) {
            appointments.addAll(generateAppointmentForDay(professionalEntity, startAppointment));
            startAppointment = startAppointment.plusDays(1);
        }

        return appointments;

    }

    private List<Appointment> generateAppointmentForDay(ProfessionalEntity professionalEntity, LocalDate startAppointment) throws AppoinmentNotGenerateException {
        List<Appointment> appointments = new ArrayList<>();

        List<AppointmentEntity> existsAppointment = appointmentRepository.
                findAppointmentByProfessionalAndDay(professionalEntity.getIdUser(), startAppointment);

        Set<LocalTime> occupiedTimes = existsAppointment.stream().
                map(AppointmentEntity::getAppointmentTime).collect(Collectors.toSet());

        for (ScheduleEntity s : professionalEntity.getScheduleEntity()) {
            if (startAppointment.getDayOfWeek().equals(s.getDay())) {
                LocalTime timeAppointment = s.getStartTime();

                while (timeAppointment.isBefore(s.getEndTime())) {

                    if(!occupiedTimes.contains(timeAppointment)){
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
    public List<AppointmentResponse> allAppointment(long idProfessional, String date){
        LocalDate dateLocal = LocalDate.parse(date);
        List<AppointmentEntity> appointmentEntities = appointmentRepository.getAllAppointmentByProfessional(idProfessional, dateLocal);
        return appointmentEntities.stream().map(appointment -> {
            AppointmentResponse appointmentResponse = new AppointmentResponse();
            appointmentResponse.setDayAppointment(appointment.getAppointmentDay());
            appointmentResponse.setTimeAppointment(appointment.getAppointmentTime());
            appointmentResponse.setStatus(appointment.getAppointmentStatus());
            appointmentResponse.setNameProfessional(String.format("%s %s",
                    appointment.getProfessional().getFirstName(),
                    appointment.getProfessional().getLastName()));

            if (appointment.getPatient() != null) {
                appointmentResponse.setNamePatient(String.format("%s %s",
                        appointment.getPatient().getFirstName(),
                        appointment.getPatient().getLastName()));
            } else {
                appointmentResponse.setNamePatient("Sin paciente");
            }

            return appointmentResponse;
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Set<LocalDate> getDateAppointment(long idProfessional) throws UserNotExistsException, AppointmentNotExistsException {
        professionalService.findProfessionalEntityById(idProfessional);

        Set<LocalDate> dateAvailable = appointmentRepository.getAvailableAppointmentDatesForProfessional(idProfessional);

        if(dateAvailable.isEmpty()) {
            throw new AppointmentNotExistsException("La fecha del turno no exite");
        }

        return dateAvailable.stream().
                filter(date -> LocalDate.now().isBefore(date)).
                collect(Collectors.toCollection(TreeSet::new));
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

        AppointmentEntity normalReservation = findAppointment(availableAppointment, appointment)
                .orElseThrow(() -> new AppointmentNotExistsException("No se encontró turno disponible para el día " +
                        appointment.getAppointmentDay() + " en el horario " + appointment.getAppointmentTime()));

        saveAppointment(normalReservation, patientEntity, specialityEntity, AppointmentStatus.RESERVADO);
    }

    private Optional<AppointmentEntity> findAppointment(List<AppointmentEntity> availableAppointment, Appointment appointment ){

        return availableAppointment.stream().
                filter(app -> app.getAppointmentDay().equals(appointment.getAppointmentDay()) &&
                        app.getAppointmentTime().equals(appointment.getAppointmentTime()))
                .findFirst();

    }

    private void appointmentFirstTime(
            List<AppointmentEntity> availableAppointment,
            Appointment appointment,
            PatientEntity patientEntity,
            SpecialityEntity specialityEntity
    ) throws AppointmentNotExistsException, AppoinmentNotGenerateException {

        AppointmentEntity firstReservation = findAppointment(availableAppointment, appointment)
                .orElseThrow(() -> new AppointmentNotExistsException("No se encontro turno disponible para el día " +
                        appointment.getAppointmentDay() + " en el horario " + appointment.getAppointmentTime()));

        LocalTime timeSecond = appointment.getAppointmentTime().plusMinutes(15);

        AppointmentEntity secondReservation = availableAppointment.stream()
                .filter(app -> app.getAppointmentDay().equals(appointment.getAppointmentDay()) &&
                        app.getAppointmentTime().equals(timeSecond))
                .findFirst()
                .orElseThrow(() -> new AppoinmentNotGenerateException("Este turno no está disponible para primera consulta"));

        saveAppointment(firstReservation, patientEntity, specialityEntity, AppointmentStatus.RESERVADO);
        saveAppointment(secondReservation, patientEntity, specialityEntity, AppointmentStatus.RESERVADO);

    }

    private void saveAppointment(AppointmentEntity appointmentEntity, PatientEntity patientEntity, SpecialityEntity specialityEntity, AppointmentStatus status){
        appointmentEntity.setPatient(patientEntity);
        appointmentEntity.setSpeciality(specialityEntity);
        appointmentEntity.setAppointmentStatus(status);
        appointmentRepository.save(appointmentEntity);
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

        nextAppointment.ifPresent(appointment -> saveAppointment(appointment, null, null, AppointmentStatus.DISPONIBLE));
        saveAppointment(appointmentEntity, null, null, AppointmentStatus.DISPONIBLE);

    }

    @Override
    @Transactional
    public void deleteAppointment(long idProfessional, DayOfWeek dayOfWeek) throws UserNotExistsException {
        int mySqlDay = mapJavaDayToMySQL(dayOfWeek);
        LocalDate today = LocalDate.now();
        appointmentRepository.deleteAppointmentsByDayOfWeekFromToday(idProfessional, mySqlDay, today);

    }

    private int mapJavaDayToMySQL(DayOfWeek dayOfWeek){
        return dayOfWeek == DayOfWeek.MONDAY ? 1 : dayOfWeek.getValue() + 1;
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
            response.setIdAppointment(appointment.getIdAppointment());
            response.setDayAppointment(appointment.getAppointmentDay());
            response.setStatus(appointment.getAppointmentStatus());
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

            return response;
        }).collect(Collectors.toList());
    }

    //tengo que hacer blockedAppointment

    @Scheduled(cron = "0 0 0 * * ?")
    public void generateAppointmenteAllProfessionals() throws AppoinmentNotGenerateException, ScheduleNotExistsException, UserNotExistsException {
        List<ProfessionalEntity> allProf = professionalService.getAllProfessionalsEntity();

        for(ProfessionalEntity p : allProf){
            appointmentGenerate(p.getIdUser());
        }
    }


    @Scheduled(cron = "0 0 0 * * ?")
    public void updateExpiredAppointments() {
        appointmentRepository.markPastAppointmentsAsExpired();
    }


}
