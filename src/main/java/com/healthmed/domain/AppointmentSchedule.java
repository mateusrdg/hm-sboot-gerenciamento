package com.healthmed.domain;

import com.healthmed.domain.dtos.schedule.AppointmentScheduleDTO;
import com.healthmed.domain.dtos.schedule.AppointmentScheduleRequestDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;


@Setter
@Getter
public class AppointmentSchedule {

    private Long id;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isBooked;
    private Doctor doctor;
    private Patient patient;

    public AppointmentSchedule() {
    }

    public AppointmentSchedule(Long id, LocalDate date, LocalTime startTime, LocalTime endTime, boolean isBooked, Doctor doctor, Patient patient) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isBooked = isBooked;
        this.doctor = doctor;
        this.patient = patient;
    }

    public AppointmentSchedule(Doctor doctor, AppointmentScheduleRequestDTO appointmentSchedule) {
        this.doctor = doctor;
        this.date = appointmentSchedule.getDate();
        this.startTime = appointmentSchedule.getStartTime();
        this.endTime = appointmentSchedule.getEndTime();
        this.isBooked = false;
        this.patient = null;
    }

    public AppointmentScheduleDTO toDTO() {
        return new AppointmentScheduleDTO(
                this.id, this.getDate(),
                this.startTime,
                this.endTime,
                this.doctor.toDto(),
                this.getPatient() != null ? this.patient.toDto() : null,
                this.isBooked);
    }

}
