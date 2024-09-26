package com.healthmed.domain.adapters.services;

import com.healthmed.application.adapters.controllers.exception.BadRequestException;
import com.healthmed.application.adapters.controllers.exception.NotFoundException;
import com.healthmed.domain.Doctor;
import com.healthmed.domain.User;
import com.healthmed.domain.dtos.doctor.DoctorDTO;
import com.healthmed.domain.ports.interfaces.AuthServicePort;
import com.healthmed.domain.ports.interfaces.DoctorServicePort;
import com.healthmed.domain.ports.repositories.DoctorRepositoryPort;

import java.util.List;
import java.util.Optional;

public class DoctorServiceImp implements DoctorServicePort {
    private final DoctorRepositoryPort repositoryPort;
    private final AuthServicePort authServicePort;

    public DoctorServiceImp(DoctorRepositoryPort repositoryPort, AuthServicePort authServicePort) {
        this.repositoryPort = repositoryPort;
        this.authServicePort = authServicePort;
    }

    @Override
    public void registerDoctor(DoctorDTO doctorDTO) {
        var doctor = new Doctor(doctorDTO);
        var user = new User(doctorDTO);

        validate(doctor);
        authServicePort.signUp(user);
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
