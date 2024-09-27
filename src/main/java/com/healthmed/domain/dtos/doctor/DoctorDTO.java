package com.healthmed.domain.dtos.doctor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DoctorDTO {
    
    @NotNull(message = "O campo 'name' não pode ser nulo")
    @NotBlank(message = "O campo 'name' não pode estar em branco")
    private String name;
    @NotNull(message = "O campo 'cpf' não pode ser nulo")
    @NotBlank(message = "O campo 'cpf' não pode estar em branco")
    private String cpf;

    @NotNull(message = "O campo 'crm' não pode ser nulo")
    @NotBlank(message = "O campo 'crm' não pode estar em branco")
    private String crm;

    @NotNull(message = "O campo 'email' não pode ser nulo")
    @NotBlank(message = "O campo 'email' não pode estar em branco")
    private String email;

    @NotNull(message = "O campo 'password' não pode ser nulo")
    @NotBlank(message = "O campo 'password' não pode estar em branco")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public DoctorDTO(String name, String cpf, String crm, String email) {
        this.name = name;
        this.cpf = cpf;
        this.crm = crm;
        this.email = email;
    }
}