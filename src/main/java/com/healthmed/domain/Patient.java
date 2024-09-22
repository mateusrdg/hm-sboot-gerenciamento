package com.healthmed.domain;

import com.healthmed.domain.dtos.patient.PatientDTO;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Patient {

    private Long id;
    private String name;
    private String cpf;
    private String email;
    private String senha;

    public Patient() {
    }

    public Patient(Long id, String name, String cpf, String email, String senha) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
    }

    public Patient(PatientDTO patientDTO) {
        this.name = patientDTO.getName();
        this.cpf = patientDTO.getCpf();
        this.email = patientDTO.getEmail();
        this.senha = patientDTO.getSenha();
    }

    public PatientDTO toDto() {
        return new PatientDTO(this.name, this.cpf, this.email, this.senha);
    }

}
