package com.healthmed.domain.dtos.schedule;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.healthmed.domain.dtos.doctor.DoctorDTO;
import com.healthmed.domain.dtos.patient.PatientDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppointmentScheduleDTO {

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime endTime;

    private DoctorDTO doctor;

    private PatientDTO patient;

    private boolean isBooked;


}
