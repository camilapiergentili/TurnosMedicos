package ar.com.dontar.demo.service;

import ar.com.dontar.demo.controller.dto.AppointmentDto;
import ar.com.dontar.demo.controller.response.AppointmentResponse;
import ar.com.dontar.demo.exception.*;

import java.time.LocalTime;
import java.util.List;

public interface AppointmentService {
    void appointmentGenerate(long idProfessional) throws UserNotExistsException, ScheduleNotExistsException, AppoinmentNotGenerateException;
    List<LocalTime> getAvailableAppointment(long idProfessional, String date) throws UserNotExistsException, AppointmentNotExistsException, AppoinmentNotGenerateException;
    List<LocalTime> getUnavailableAppointment(long idProfessional, String date) throws UserNotExistsException, AppoinmentNotGenerateException;
    void bookAppointment(AppointmentDto appointmentDto) throws UserNotExistsException, AppointmentNotExistsException, AppoinmentNotGenerateException, SpecialityNotExistsException;
    void cancelAppointment(long idPatient, long idAppointment) throws UserNotExistsException, AppointmentNotExistsException, CancellationTimeExceededException;
    List<AppointmentResponse> getAppointmentsByPatient(long idPatient) throws UserNotExistsException;
}
