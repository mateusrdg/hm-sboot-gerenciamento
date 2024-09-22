package com.healthmed.domain.ports.interfaces;

import com.healthmed.domain.dtos.schedule.AppointmentScheduleDTO;
import com.healthmed.domain.dtos.schedule.AppointmentScheduleRequestDTO;

import java.util.List;

public interface ScheduleServicePort {
    void createSchedule(String doctorCpf, List<AppointmentScheduleRequestDTO> schedules);

    void updateSchedule(String doctorCpf, Long scheduleId, AppointmentScheduleRequestDTO scheduleRequest);

    void deleteSchedule(String doctorCpf, Long scheduleId);

    List<AppointmentScheduleDTO> getAvailableSchedules(String doctorCpf);

    void bookAppointment(String doctorCpf, Long scheduleId, String patientCpf);
}
