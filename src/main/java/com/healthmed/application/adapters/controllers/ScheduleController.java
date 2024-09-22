package com.healthmed.application.adapters.controllers;

import com.healthmed.domain.dtos.schedule.AppointmentScheduleDTO;
import com.healthmed.domain.dtos.schedule.AppointmentScheduleRequestDTO;
import com.healthmed.domain.ports.interfaces.ScheduleServicePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleServicePort servicePort;

    public ScheduleController(ScheduleServicePort servicePort) {
        this.servicePort = servicePort;
    }

    @PostMapping("/doctors/{doctorCpf}/schedule")
    public ResponseEntity<String> createSchedule(@PathVariable String doctorCpf, @RequestBody List<AppointmentScheduleRequestDTO> schedules) {
        servicePort.createSchedule(doctorCpf, schedules);
        return ResponseEntity.ok("Schedule created successfully");
    }

    @PutMapping("/doctors/{doctorCpf}/schedule/{scheduleId}")
    public ResponseEntity<String> updateSchedule(@PathVariable String doctorCpf, @PathVariable Long scheduleId, @RequestBody AppointmentScheduleRequestDTO scheduleRequest) {
        servicePort.updateSchedule(doctorCpf, scheduleId, scheduleRequest);
        return ResponseEntity.ok("Schedule updated successfully");
    }

    @DeleteMapping("/doctors/{doctorCpf}/schedule/{scheduleId}")
    public ResponseEntity<String> deleteSchedule(@PathVariable String doctorCpf, @PathVariable Long scheduleId) {
        servicePort.deleteSchedule(doctorCpf, scheduleId);
        return ResponseEntity.ok("Schedule deleted successfully");
    }

    @GetMapping("/doctors/{doctorCpf}/schedules")
    public ResponseEntity<List<AppointmentScheduleDTO>> getAvailableSchedules(@PathVariable String doctorCpf) {
        List<AppointmentScheduleDTO> schedules = servicePort.getAvailableSchedules(doctorCpf);
        return ResponseEntity.ok(schedules);
    }

    @PostMapping("/doctors/{doctorCpf}/schedules/{scheduleId}/book")
    public ResponseEntity<String> bookAppointment(
            @PathVariable String doctorCpf,
            @PathVariable Long scheduleId,
            @RequestBody String patientCpf) {
        servicePort.bookAppointment(doctorCpf, scheduleId, patientCpf);
        return ResponseEntity.ok("Appointment booked successfully");
    }

}
