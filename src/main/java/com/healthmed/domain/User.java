package com.healthmed.domain;

import com.healthmed.domain.dtos.doctor.DoctorDTO;
import com.healthmed.domain.dtos.enums.UserType;
import com.healthmed.domain.dtos.patient.PatientDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String email;
    private String password;
    private UserType userType;

    public User(DoctorDTO dto) {
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.userType = UserType.MEDICO;
    }

    public User(PatientDTO dto) {
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.userType = UserType.PACIENTE;
    }
}
