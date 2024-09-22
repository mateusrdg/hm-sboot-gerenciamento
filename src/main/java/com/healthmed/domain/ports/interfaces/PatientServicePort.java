package com.healthmed.domain.ports.interfaces;

import com.healthmed.application.adapters.controllers.exception.NotFoundException;
import com.healthmed.domain.dtos.patient.PatientDTO;

public interface PatientServicePort {
    PatientDTO findPatientByCpf(String cpf)throws NotFoundException;
    void registerPatient(PatientDTO doctorDTO);
}
