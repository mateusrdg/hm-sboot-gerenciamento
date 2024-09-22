package com.healthmed.domain.adapters.services;

import com.healthmed.application.adapters.controllers.exception.BadRequestException;
import com.healthmed.application.adapters.controllers.exception.NotFoundException;
import com.healthmed.domain.Doctor;
import com.healthmed.domain.dtos.doctor.DoctorDTO;
import com.healthmed.domain.ports.interfaces.DoctorServicePort;
import com.healthmed.domain.ports.repositories.DoctorRepositoryPort;

import java.util.List;
import java.util.Optional;

public class DoctorServiceImp implements DoctorServicePort {

    private final DoctorRepositoryPort repositoryPort;

    public DoctorServiceImp(DoctorRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public void registerDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor(doctorDTO);
        validate(doctor);
        repositoryPort.save(doctor);
    }

    @Override
    public List<DoctorDTO> findAll() {
        return repositoryPort.findAll().stream().map(Doctor::toDto).toList();
    }

    private void validate(Doctor doctor) {
        Optional<Doctor> optionalDoctor = repositoryPort.findByCpf(doctor.getCpf());
        optionalDoctor.ifPresent(doctorExists -> {throw new BadRequestException("CPF já cadastrado");});
    }

    @Override
    public DoctorDTO findDoctorByCpf(String cpf) {
        Optional<Doctor> customer = repositoryPort.findByCpf(cpf);
        return customer.map(Doctor::toDto).orElseThrow(() -> new NotFoundException("Medico não existe"));
    }

}
