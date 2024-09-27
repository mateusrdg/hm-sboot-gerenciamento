package com.healthmed.infrastructure.adapters.repositories;

import com.healthmed.domain.AppointmentSchedule;
import com.healthmed.domain.Doctor;
import com.healthmed.infrastructure.adapters.entities.AppointmentScheduleEntity;
import com.healthmed.infrastructure.adapters.entities.DoctorEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleRepositoryTest {

    @InjectMocks
    private ScheduleRepository repository;

    @Mock
    private ScheduleJpaRepository jpaRepository;

    @Test
    void testFindByIdFound() {
        Long scheduleId = 1L;
        AppointmentScheduleEntity entity = new AppointmentScheduleEntity();
        entity.setDoctor(getDoctorEntity());
        when(jpaRepository.findById(scheduleId)).thenReturn(Optional.of(entity));

        Optional<AppointmentSchedule> result = repository.findById(scheduleId);

        assertTrue(result.isPresent());
        verify(jpaRepository, times(1)).findById(scheduleId);
    }

    @Test
    void testFindByIdNotFound() {
        Long scheduleId = 1L;
        when(jpaRepository.findById(scheduleId)).thenReturn(Optional.empty());

        Optional<AppointmentSchedule> result = repository.findById(scheduleId);

        assertFalse(result.isPresent());
        verify(jpaRepository, times(1)).findById(scheduleId);
    }

    @Test
    void testSaveSuccess() {
        var appointmentSchedule = new AppointmentSchedule();
        appointmentSchedule.setDoctor(getDoctor());

        repository.save(appointmentSchedule);

        verify(jpaRepository, times(1)).save(any(AppointmentScheduleEntity.class));
    }

    @Test
    void testDeleteSuccess() {
        Long scheduleId = 1L;

        repository.delete(scheduleId);

        verify(jpaRepository, times(1)).deleteById(scheduleId);
    }

    @Test
    void testSaveAllSuccess() {
        AppointmentSchedule appointmentSchedule1 = new AppointmentSchedule();
        appointmentSchedule1.setDoctor(getDoctor());
        AppointmentSchedule appointmentSchedule2 = new AppointmentSchedule();
        appointmentSchedule2.setDoctor(getDoctor());
        List<AppointmentSchedule> schedules = List.of(appointmentSchedule1, appointmentSchedule2);

        repository.saveAll(schedules);

        verify(jpaRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testFindAllAvailableSchedules() {
        String doctorCpf = "123456789";
        AppointmentScheduleEntity entity1 = new AppointmentScheduleEntity();
        entity1.setDoctor(getDoctorEntity());
        AppointmentScheduleEntity entity2 = new AppointmentScheduleEntity();
        entity2.setDoctor(getDoctorEntity());
        when(jpaRepository.findAllByDoctorCpfAndIsBookedIsFalse(doctorCpf)).thenReturn(List.of(entity1, entity2));

        List<AppointmentSchedule> result = repository.findAllAvaliableSchedules(doctorCpf);

        assertEquals(2, result.size());
        verify(jpaRepository, times(1)).findAllByDoctorCpfAndIsBookedIsFalse(doctorCpf);
    }

    @Test
    void testFindByIdAndDoctorCpfFound() {
        Long scheduleId = 1L;
        String doctorCpf = "123456789";
        AppointmentScheduleEntity entity = new AppointmentScheduleEntity();
        entity.setDoctor(getDoctorEntity());
        when(jpaRepository.findByIdAndDoctorCpf(scheduleId, doctorCpf)).thenReturn(Optional.of(entity));

        Optional<AppointmentSchedule> result = repository.findByIdAndDoctorCpf(scheduleId, doctorCpf);

        assertTrue(result.isPresent());
        verify(jpaRepository, times(1)).findByIdAndDoctorCpf(scheduleId, doctorCpf);
    }

    @Test
    void testFindByIdAndDoctorCpfNotFound() {
        Long scheduleId = 1L;
        String doctorCpf = "123456789";
        when(jpaRepository.findByIdAndDoctorCpf(scheduleId, doctorCpf)).thenReturn(Optional.empty());

        Optional<AppointmentSchedule> result = repository.findByIdAndDoctorCpf(scheduleId, doctorCpf);

        assertFalse(result.isPresent());
        verify(jpaRepository, times(1)).findByIdAndDoctorCpf(scheduleId, doctorCpf);
    }

    @Test
    void testFindAvailableScheduleByIdAndDoctorCpfFound() {
        Long scheduleId = 1L;
        String doctorCpf = "123456789";
        AppointmentScheduleEntity entity = new AppointmentScheduleEntity();
        entity.setDoctor(getDoctorEntity());
        when(jpaRepository.findByIdAndDoctorCpfAndIsBookedIsFalse(scheduleId, doctorCpf)).thenReturn(Optional.of(entity));

        Optional<AppointmentSchedule> result = repository.findAvaliableScheduleByIdAndDoctorCpf(scheduleId, doctorCpf);

        assertTrue(result.isPresent());
        verify(jpaRepository, times(1)).findByIdAndDoctorCpfAndIsBookedIsFalse(scheduleId, doctorCpf);
    }

    @Test
    void testFindAvailableScheduleByIdAndDoctorCpfNotFound() {
        Long scheduleId = 1L;
        String doctorCpf = "123456789";
        when(jpaRepository.findByIdAndDoctorCpfAndIsBookedIsFalse(scheduleId, doctorCpf)).thenReturn(Optional.empty());

        Optional<AppointmentSchedule> result = repository.findAvaliableScheduleByIdAndDoctorCpf(scheduleId, doctorCpf);

        assertFalse(result.isPresent());
        verify(jpaRepository, times(1)).findByIdAndDoctorCpfAndIsBookedIsFalse(scheduleId, doctorCpf);
    }

    private DoctorEntity getDoctorEntity() {
        var doctor = new DoctorEntity();
        doctor.setId(1L);
        return doctor;
    }

    private Doctor getDoctor() {
        var doctor = new Doctor();
        doctor.setId(1L);
        return doctor;
    }
}
