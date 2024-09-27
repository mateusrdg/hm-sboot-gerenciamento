package com.healthmed.domain.adapters.services;

import com.healthmed.application.adapters.controllers.exception.NotFoundException;
import com.healthmed.domain.AppointmentSchedule;
import com.healthmed.domain.Doctor;
import com.healthmed.domain.Patient;
import com.healthmed.domain.dtos.schedule.AppointmentScheduleDTO;
import com.healthmed.domain.dtos.schedule.AppointmentScheduleRequestDTO;
import com.healthmed.domain.ports.repositories.DoctorRepositoryPort;
import com.healthmed.domain.ports.repositories.PatientRepositoryPort;
import com.healthmed.domain.ports.repositories.ScheduleRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ScheduleServiceImpTest {

    @InjectMocks
    private ScheduleServiceImp service;

    @Mock
    private ScheduleRepositoryPort scheduleRepositoryPort;

    @Mock
    private DoctorRepositoryPort doctorRepositoryPort;

    @Mock
    private PatientRepositoryPort patientRepositoryPort;

    @Test
    void testCreateScheduleSuccess() {
        String doctorCpf = "12345678901";
        AppointmentScheduleRequestDTO scheduleRequest = new AppointmentScheduleRequestDTO();
        scheduleRequest.setDate(LocalDate.now());
        scheduleRequest.setStartTime(LocalTime.now().plusHours(1));
        scheduleRequest.setEndTime(LocalTime.now().plusHours(2));

        Doctor doctor = new Doctor();
        when(doctorRepositoryPort.findByCpf(doctorCpf)).thenReturn(Optional.of(doctor));
        doNothing().when(scheduleRepositoryPort).saveAll(any(List.class));

        service.createSchedule(doctorCpf, List.of(scheduleRequest));

        verify(scheduleRepositoryPort, times(1)).saveAll(any(List.class));
    }

    @Test
    void testCreateScheduleDoctorNotFound() {
        String doctorCpf = "12345678901";
        AppointmentScheduleRequestDTO scheduleRequest = new AppointmentScheduleRequestDTO();
        when(doctorRepositoryPort.findByCpf(doctorCpf)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.createSchedule(doctorCpf, List.of(scheduleRequest)));
    }

    @Test
    void testUpdateScheduleSuccess() {
        String doctorCpf = "12345678901";
        Long scheduleId = 1L;
        AppointmentScheduleRequestDTO scheduleRequest = new AppointmentScheduleRequestDTO();
        scheduleRequest.setDate(LocalDate.now());
        scheduleRequest.setStartTime(LocalTime.now().plusHours(1));
        scheduleRequest.setEndTime(LocalTime.now().plusHours(2));

        AppointmentSchedule schedule = new AppointmentSchedule();
        when(scheduleRepositoryPort.findByIdAndDoctorCpf(scheduleId, doctorCpf)).thenReturn(Optional.of(schedule));
        doNothing().when(scheduleRepositoryPort).save(any(AppointmentSchedule.class));

        service.updateSchedule(doctorCpf, scheduleId, scheduleRequest);

        verify(scheduleRepositoryPort, times(1)).save(any(AppointmentSchedule.class));
    }

    @Test
    void testUpdateScheduleNotFound() {
        String doctorCpf = "12345678901";
        Long scheduleId = 1L;
        AppointmentScheduleRequestDTO scheduleRequest = new AppointmentScheduleRequestDTO();
        when(scheduleRepositoryPort.findByIdAndDoctorCpf(scheduleId, doctorCpf)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.updateSchedule(doctorCpf, scheduleId, scheduleRequest));
    }

    @Test
    void testDeleteScheduleSuccess() {
        String doctorCpf = "12345678901";
        Long scheduleId = 1L;
        AppointmentSchedule schedule = new AppointmentSchedule();
        when(scheduleRepositoryPort.findByIdAndDoctorCpf(scheduleId, doctorCpf)).thenReturn(Optional.of(schedule));
        doNothing().when(scheduleRepositoryPort).delete(any(Long.class));

        service.deleteSchedule(doctorCpf, scheduleId);

        verify(scheduleRepositoryPort, times(1)).delete(scheduleId);
    }

    @Test
    void testDeleteScheduleNotFound() {
        String doctorCpf = "12345678901";
        Long scheduleId = 1L;
        when(scheduleRepositoryPort.findByIdAndDoctorCpf(scheduleId, doctorCpf)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.deleteSchedule(doctorCpf, scheduleId));
    }

    @Test
    void testGetAvailableSchedulesSuccess() {
        String doctorCpf = "12345678901";
        Doctor doctor = new Doctor();
        doctor.setCpf(doctorCpf);

        AppointmentSchedule schedule = new AppointmentSchedule();
        schedule.setDoctor(doctor);


        // Configure os mocks
        when(doctorRepositoryPort.findByCpf(doctorCpf)).thenReturn(Optional.of(doctor));
        when(scheduleRepositoryPort.findAllAvaliableSchedules(doctorCpf)).thenReturn(List.of(schedule));

        // Execute o método do serviço
        List<AppointmentScheduleDTO> result = service.getAvailableSchedules(doctorCpf);

        // Verifique os resultados
        verify(scheduleRepositoryPort, times(1)).findAllAvaliableSchedules(doctorCpf);
        assertEquals(1, result.size());
        assertEquals(doctorCpf, result.get(0).getDoctor().getCpf()); // Verifique se o DTO retornado é o esperado
    }


    @Test
    void testGetAvailableSchedulesDoctorNotFound() {
        String doctorCpf = "12345678901";
        when(doctorRepositoryPort.findByCpf(doctorCpf)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getAvailableSchedules(doctorCpf));
    }

    @Test
    void testBookAppointmentSuccess() {
        String doctorCpf = "12345678901";
        Long scheduleId = 1L;
        String patientCpf = "98765432100";
        AppointmentSchedule schedule = new AppointmentSchedule();
        Patient patient = new Patient();

        when(scheduleRepositoryPort.findAvaliableScheduleByIdAndDoctorCpf(scheduleId, doctorCpf)).thenReturn(Optional.of(schedule));
        when(patientRepositoryPort.findByCpf(patientCpf)).thenReturn(Optional.of(patient));
        doNothing().when(scheduleRepositoryPort).save(any(AppointmentSchedule.class));

        service.bookAppointment(doctorCpf, scheduleId, patientCpf);

        verify(scheduleRepositoryPort, times(1)).save(any(AppointmentSchedule.class));
    }

    @Test
    void testBookAppointmentScheduleNotFound() {
        String doctorCpf = "12345678901";
        Long scheduleId = 1L;
        String patientCpf = "98765432100";
        when(scheduleRepositoryPort.findAvaliableScheduleByIdAndDoctorCpf(scheduleId, doctorCpf)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.bookAppointment(doctorCpf, scheduleId, patientCpf));
    }

    @Test
    void testBookAppointmentPatientNotFound() {
        String doctorCpf = "12345678901";
        Long scheduleId = 1L;
        String patientCpf = "98765432100";
        AppointmentSchedule schedule = new AppointmentSchedule();

        when(scheduleRepositoryPort.findAvaliableScheduleByIdAndDoctorCpf(scheduleId, doctorCpf)).thenReturn(Optional.of(schedule));
        when(patientRepositoryPort.findByCpf(patientCpf)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.bookAppointment(doctorCpf, scheduleId, patientCpf));
    }
}
