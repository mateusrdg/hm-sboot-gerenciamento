package com.healthmed.infrastructure.adapters.repositories;

import com.healthmed.infrastructure.adapters.entities.AppointmentScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;


@Repository
public interface ScheduleJpaRepository extends JpaRepository<AppointmentScheduleEntity, Long> {
    List<AppointmentScheduleEntity> findAllByDoctorCpfAndIsBookedIsFalse(String cpf);
    Optional<AppointmentScheduleEntity> findByIdAndDoctorCpf(Long scheduleId, String doctorCpf);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<AppointmentScheduleEntity> findByIdAndDoctorCpfAndIsBookedIsFalse(Long scheduleId, String doctorCpf);
}
