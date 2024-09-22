package com.healthmed.domain.adapters.services;

import com.healthmed.application.adapters.controllers.exception.BadRequestException;
import com.healthmed.application.adapters.controllers.exception.NotFoundException;
import com.healthmed.domain.Patient;
import com.healthmed.domain.dtos.patient.PatientDTO;
import com.healthmed.domain.ports.interfaces.PatientServicePort;
import com.healthmed.domain.ports.repositories.PatientRepositoryPort;

import java.util.Optional;

public class PatientServiceImp implements PatientServicePort {

    private final PatientRepositoryPort repositoryPort;

    public PatientServiceImp(PatientRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public void registerPatient(PatientDTO patientDTO) {
        Patient patient = new Patient(patientDTO);
        validate(patient);
        this.repositoryPort.save(patient);
    }

    private void validate(Patient patient) {
        Optional<Patient> optionalPatient = this.repositoryPort.findByCpf(patient.getCpf());
        optionalPatient.ifPresent(patientExists -> {throw new BadRequestException("CPF já cadastrado");});
    }

    @Override
    public PatientDTO findPatientByCpf(String cpf) {
        Optional<Patient> patient = this.repositoryPort.findByCpf(cpf);
        return patient.map(Patient::toDto).orElseThrow(() -> new NotFoundException("Paciente não existe"));
    }
}
