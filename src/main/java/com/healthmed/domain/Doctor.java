package com.healthmed.domain;

import com.healthmed.domain.dtos.doctor.DoctorDTO;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Doctor {

    private Long id;
    private String name;
    private String cpf;
    private String crm;
    private String email;
    private String senha;

    public Doctor() {
    }

    public Doctor(Long id, String name, String cpf, String crm, String email, String senha) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.crm = crm;
        this.email = email;
        this.senha = senha;
    }

    public Doctor(DoctorDTO doctorDTO) {
        this.name = doctorDTO.getName();
        this.cpf = doctorDTO.getCpf();
        this.crm = doctorDTO.getCrm();
        this.email = doctorDTO.getEmail();
        this.senha = doctorDTO.getSenha();
    }

    public DoctorDTO toDto() {
        return new DoctorDTO(this.name, this.cpf, this.crm, this.email, this.senha);
    }

}
