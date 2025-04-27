package ar.com.dontar.demo.mapper;


import ar.com.dontar.demo.controller.dto.AppointmentDto;
import ar.com.dontar.demo.model.Appointment;
import ar.com.dontar.demo.persistence.entity.AppointmentEntity;

import java.time.LocalDate;
import java.time.LocalTime;

public class MapperAppointment {

    public static Appointment appointmentDtoToModel(AppointmentDto appointmentDto){
        Appointment appointment = new Appointment();
        appointment.setAppointmentDay(LocalDate.parse(appointmentDto.getAppointmentDay()));
        appointment.setAppointmentTime(LocalTime.parse(appointmentDto.getAppointmentTime()));

        return  appointment;
    }

    public static AppointmentEntity appointmentModelToEntity(Appointment appointment){
        AppointmentEntity appointmentEntity = new AppointmentEntity();
        appointmentEntity.setAppointmentDay(appointment.getAppointmentDay());
        appointmentEntity.setAppointmentTime(appointment.getAppointmentTime());
        appointmentEntity.setAppointmentStatus(appointment.getAppointmentStatus());


        return appointmentEntity;
    }

    public static Appointment appointmentEntityToModel(AppointmentEntity appointmentEntity){
        Appointment appointment = new Appointment();
        appointment.setIdAppointment(appointmentEntity.getIdAppointment());
        appointment.setAppointmentDay(appointmentEntity.getAppointmentDay());
        appointment.setAppointmentTime(appointmentEntity.getAppointmentTime());
        appointment.setAppointmentStatus(appointmentEntity.getAppointmentStatus());

        return appointment;
    }
}
