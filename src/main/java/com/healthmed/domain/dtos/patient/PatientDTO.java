package com.healthmed.domain.dtos.patient;

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
public class PatientDTO {

    @NotNull(message = "O campo 'nome' não pode ser nulo")
    @NotBlank(message = "O campo 'nome' não pode estar em branco")
    private String name;
    @NotNull(message = "O campo 'cpf' não pode ser nulo")
    @NotBlank(message = "O campo 'cpf' não pode estar em branco")
    private String cpf;

    @NotNull(message = "O campo 'email' não pode ser nulo")
    @NotBlank(message = "O campo 'email' não pode estar em branco")
    private String email;

    @NotNull(message = "O campo 'senha' não pode ser nulo")
    @NotBlank(message = "O campo 'senha' não pode estar em branco")
    private String senha;

}
