package com.healthmed.infrastructure.adapters.entities;

import com.healthmed.domain.AppointmentSchedule;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointment_schedules")
@Getter
@Setter
public class AppointmentScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    
    private boolean isBooked;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = true)
    private PatientEntity patient;


    public AppointmentScheduleEntity() {

    }

    public AppointmentScheduleEntity(AppointmentSchedule appointmentSchedule) {
        this.id = appointmentSchedule.getId();
        this.date = appointmentSchedule.getDate();
        this.startTime = appointmentSchedule.getStartTime();
        this.endTime = appointmentSchedule.getEndTime();
        this.isBooked = appointmentSchedule.isBooked();
        this.doctor = new DoctorEntity(appointmentSchedule.getDoctor());
        this.patient = appointmentSchedule.getPatient() != null ? new PatientEntity(appointmentSchedule.getPatient()) : null;
    }

    public AppointmentSchedule toDomain() {
        return new AppointmentSchedule(
                this.id,
                this.date,
                this.startTime,
                this.endTime,
                this.isBooked,
                this.doctor.toDomain(),
                this.patient != null ? this.patient.toDomain() : null);
    }
}
