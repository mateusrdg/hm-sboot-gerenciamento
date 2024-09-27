package com.healthmed.domain.adapters.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthmed.application.adapters.controllers.exception.NotFoundException;
import com.healthmed.domain.AppointmentSchedule;
import com.healthmed.domain.Doctor;
import com.healthmed.domain.Patient;
import com.healthmed.domain.dtos.schedule.AppointmentScheduleDTO;
import com.healthmed.domain.dtos.schedule.AppointmentScheduleRequestDTO;
import com.healthmed.domain.ports.interfaces.ScheduleServicePort;
import com.healthmed.domain.ports.repositories.DoctorRepositoryPort;
import com.healthmed.domain.ports.repositories.PatientRepositoryPort;
import com.healthmed.domain.ports.repositories.ScheduleRepositoryPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;

public class ScheduleServiceImp implements ScheduleServicePort {

    private final ScheduleRepositoryPort repositoryPort;
    private final DoctorRepositoryPort doctorRepositoryPort;
    private final PatientRepositoryPort patientRepositoryPort;
    private final QueueMessagingTemplate queueMessagingTemplate;
    private final ObjectMapper objectMapper;

    @Value("${cloud.aws.end-point.uri}")
    private String endpoint;

    public ScheduleServiceImp(ScheduleRepositoryPort repositoryPort, DoctorRepositoryPort doctorRepositoryPort, PatientRepositoryPort patientRepositoryPort, QueueMessagingTemplate queueMessagingTemplate, ObjectMapper objectMapper) {
        this.repositoryPort = repositoryPort;
        this.doctorRepositoryPort = doctorRepositoryPort;
        this.patientRepositoryPort = patientRepositoryPort;
        this.queueMessagingTemplate = queueMessagingTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void createSchedule(String doctorCpf, List<AppointmentScheduleRequestDTO> schedules) {
        var doctor = findDoctor(doctorCpf);
        var appointmentSchedules = schedules.stream().map(appointmentSchedule -> new AppointmentSchedule(doctor, appointmentSchedule)).toList();
        repositoryPort.saveAll(appointmentSchedules);

    }

    @Override
    public void updateSchedule(String doctorCpf, Long scheduleId, AppointmentScheduleRequestDTO scheduleRequest) {
        var appointmentSchedule = findAppointmentSchedule(scheduleId, doctorCpf);
        appointmentSchedule.setDate(scheduleRequest.getDate());
        appointmentSchedule.setStartTime(scheduleRequest.getStartTime());
        appointmentSchedule.setEndTime(scheduleRequest.getEndTime());
        repositoryPort.save(appointmentSchedule);
    }

    private AppointmentSchedule findAppointmentSchedule(Long scheduleId, String doctorCpf) {
        return repositoryPort.findByIdAndDoctorCpf(scheduleId, doctorCpf).orElseThrow(() -> new NotFoundException("Horário de agendamento nao existe"));
    }

    @Override
    public void deleteSchedule(String doctorCpf, Long scheduleId) {
        findAppointmentSchedule(scheduleId, doctorCpf);
        repositoryPort.delete(scheduleId);
    }

    @Override
    public List<AppointmentScheduleDTO> getAvailableSchedules(String doctorCpf) {
        findDoctor(doctorCpf);
        return repositoryPort.findAllAvaliableSchedules(doctorCpf).stream().map(AppointmentSchedule::toDTO).toList();
    }

    @Override
    public void bookAppointment(String doctorCpf, Long scheduleId, String patientCpf) {
        var appointmentSchedule = findAvaliableAppointmentSchedule(scheduleId, doctorCpf);
        var patient = findPatient(patientCpf);
        appointmentSchedule.setBooked(true);
        appointmentSchedule.setPatient(patient);
        repositoryPort.save(appointmentSchedule);
        sendAppointmentSchedule(appointmentSchedule);
    }

    private AppointmentSchedule findAvaliableAppointmentSchedule(Long scheduleId, String doctorCpf) {
        return repositoryPort.findAvaliableScheduleByIdAndDoctorCpf(scheduleId, doctorCpf).orElseThrow(() -> new NotFoundException("Horário de agendamento nao existe ou esta indisponível"));
    }

    private Patient findPatient(String patientCpf) {
        return patientRepositoryPort.findByCpf(patientCpf).orElseThrow(()-> new NotFoundException("Paciente não existe"));
    }

    private Doctor findDoctor(String doctorCpf) {
        return doctorRepositoryPort.findByCpf(doctorCpf).orElseThrow(()-> new NotFoundException("Medico não existe"));
    }

    public void sendAppointmentSchedule(AppointmentSchedule appointmentSchedule) {
        try {
            String message = objectMapper.writeValueAsString(appointmentSchedule);
            queueMessagingTemplate.send(endpoint, MessageBuilder.withPayload(message).build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
