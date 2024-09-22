package com.healthmed.infrastructure.adapters.repositories;

import com.healthmed.domain.AppointmentSchedule;
import com.healthmed.domain.ports.repositories.ScheduleRepositoryPort;
import com.healthmed.infrastructure.adapters.entities.AppointmentScheduleEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ScheduleRepository implements ScheduleRepositoryPort {

    private final ScheduleJpaRepository jpaRepository;

    public ScheduleRepository(ScheduleJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<AppointmentSchedule> findById(Long scheduleId) {
        var optionalAppointmentSchedule = jpaRepository.findById(scheduleId);
        return optionalAppointmentSchedule.map(AppointmentScheduleEntity::toDomain);
    }

    @Override
    public void save(AppointmentSchedule appointmentSchedule) {
        jpaRepository.save(new AppointmentScheduleEntity(appointmentSchedule));
    }

    @Override
    public void delete(Long scheduleId) {
        jpaRepository.deleteById(scheduleId);
    }

    @Override
    public void saveAll(List<AppointmentSchedule> appointmentSchedules) {
        jpaRepository.saveAll(appointmentSchedules.stream().map(AppointmentScheduleEntity::new).toList());
    }

    @Override
    public List<AppointmentSchedule> findAllAvaliableSchedules(String doctorCpf) {
        var appointmentScheduleEntities = jpaRepository.findAllByDoctorCpfAndIsBookedIsFalse(doctorCpf);
        return appointmentScheduleEntities.stream().map(AppointmentScheduleEntity::toDomain).toList();
    }

    @Override
    public Optional<AppointmentSchedule> findByIdAndDoctorCpf(Long scheduleId, String doctorCpf) {
        var optionalAppointmentSchedule = jpaRepository.findByIdAndDoctorCpf(scheduleId, doctorCpf);
        return optionalAppointmentSchedule.map(AppointmentScheduleEntity::toDomain);
    }

    @Override
    public Optional<AppointmentSchedule> findAvaliableScheduleByIdAndDoctorCpf(Long scheduleId, String doctorCpf) {
        var optionalAppointmentSchedule = jpaRepository.findByIdAndDoctorCpfAndIsBookedIsFalse(scheduleId, doctorCpf);
        return optionalAppointmentSchedule.map(AppointmentScheduleEntity::toDomain);
    }


}
