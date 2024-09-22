package com.healthmed.application.adapters.controllers;

import com.healthmed.application.adapters.controllers.exception.NotFoundException;
import com.healthmed.domain.dtos.patient.PatientDTO;
import com.healthmed.domain.ports.interfaces.PatientServicePort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("patient")
public class PatientController {
    private final PatientServicePort servicePort;

    public PatientController(PatientServicePort servicePort) {
        this.servicePort = servicePort;
    }

    @PostMapping
    void registerPatient(@RequestBody @Valid PatientDTO patientDTO) {
        servicePort.registerPatient(patientDTO);
    }

    @GetMapping(value = "/{cpf}")
    PatientDTO findPatientByCpf(@PathVariable String cpf) throws NotFoundException {
        return servicePort.findPatientByCpf(cpf);
    }

}
