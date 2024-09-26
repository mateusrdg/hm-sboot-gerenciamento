package com.healthmed.infrastructure.adapters.entities;

import com.healthmed.domain.Doctor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "doctors")
@Getter
@Setter
public class DoctorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String cpf;
    private String crm;
    private String email;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<AppointmentScheduleEntity> schedules;

    public DoctorEntity() {
    }

    public DoctorEntity(Doctor doctor) {
        this.id = doctor.getId();
        this.name = doctor.getName();
        this.cpf = doctor.getCpf();
        this.crm = doctor.getCrm();
        this.email = doctor.getEmail();
    }

    public Doctor toDomain() {
        return new Doctor(this.id, this.name, this.cpf, this.crm, this.email);
    }

}
