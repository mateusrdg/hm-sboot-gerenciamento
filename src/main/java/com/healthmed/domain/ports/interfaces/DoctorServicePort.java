package com.healthmed.domain.ports.interfaces;

import com.healthmed.application.adapters.controllers.exception.NotFoundException;
import com.healthmed.domain.dtos.doctor.DoctorDTO;

import java.util.List;

public interface DoctorServicePort {
    DoctorDTO findDoctorByCpf(String cpf)throws NotFoundException;
    void registerDoctor(DoctorDTO doctorDTO);
    List<DoctorDTO> findAll();

}
