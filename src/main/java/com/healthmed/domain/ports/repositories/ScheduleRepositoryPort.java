package com.healthmed.domain.ports.repositories;

import com.healthmed.domain.AppointmentSchedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepositoryPort {
    void saveAll(List<AppointmentSchedule> appointmentSchedules);

    Optional<AppointmentSchedule> findById(Long scheduleId);

    void save(AppointmentSchedule appointmentSchedule);

    void delete(Long scheduleId);

    List<AppointmentSchedule> findAllAvaliableSchedules(String doctorCpf);

    Optional<AppointmentSchedule> findByIdAndDoctorCpf(Long scheduleId, String doctorCpf);

    Optional<AppointmentSchedule> findAvaliableScheduleByIdAndDoctorCpf(Long scheduleId, String doctorCpf);
}
