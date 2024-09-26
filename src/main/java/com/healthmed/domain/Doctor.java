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

    public Doctor() {
    }

    public Doctor(Long id, String name, String cpf, String crm, String email) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.crm = crm;
        this.email = email;
    }

    public Doctor(DoctorDTO doctorDTO) {
        this.name = doctorDTO.getName();
        this.cpf = doctorDTO.getCpf();
        this.crm = doctorDTO.getCrm();
        this.email = doctorDTO.getEmail();
    }

    public DoctorDTO toDto() {
        return new DoctorDTO(this.name, this.cpf, this.crm, this.email);
    }

}
