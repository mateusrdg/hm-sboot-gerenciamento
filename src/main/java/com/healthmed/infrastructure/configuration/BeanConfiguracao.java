package com.healthmed.infrastructure.configuration;

import com.healthmed.domain.adapters.services.DoctorServiceImp;
import com.healthmed.domain.adapters.services.PatientServiceImp;
import com.healthmed.domain.adapters.services.ScheduleServiceImp;
import com.healthmed.domain.ports.interfaces.DoctorServicePort;
import com.healthmed.domain.ports.interfaces.PatientServicePort;
import com.healthmed.domain.ports.interfaces.ScheduleServicePort;
import com.healthmed.domain.ports.repositories.DoctorRepositoryPort;
import com.healthmed.domain.ports.repositories.PatientRepositoryPort;
import com.healthmed.domain.ports.repositories.ScheduleRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguracao {

    @Bean
    PatientServicePort patientServicePort(PatientRepositoryPort repositoryPort) {
        return new PatientServiceImp(repositoryPort);
    }

    @Bean
    DoctorServicePort doctorServicePort(DoctorRepositoryPort repositoryPort) {
        return new DoctorServiceImp(repositoryPort);
    }

    @Bean
    ScheduleServicePort scheduleServicePort(ScheduleRepositoryPort repositoryPort, DoctorRepositoryPort doctorRepositoryPort, PatientRepositoryPort patientRepositoryPort) {
        return new ScheduleServiceImp(repositoryPort, doctorRepositoryPort, patientRepositoryPort);
    }
}
