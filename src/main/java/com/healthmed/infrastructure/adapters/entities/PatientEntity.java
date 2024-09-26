package com.healthmed.infrastructure.adapters.entities;

import com.healthmed.domain.Patient;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "patients")
@Getter
@Setter
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String cpf;
    private String email;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<AppointmentScheduleEntity> appointments;

    public PatientEntity() {
    }

    public PatientEntity(Patient patient) {
        this.id = patient.getId();
        this.name = patient.getName();
        this.cpf = patient.getCpf();
        this.email = patient.getEmail();
    }

    public Patient toDomain() {
        return new Patient(this.id, this.name, this.cpf, this.email);
    }

}
