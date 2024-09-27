package com.healthmed.application.adapters.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthmed.domain.dtos.patient.PatientDTO;
import com.healthmed.domain.ports.interfaces.PatientServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PatientControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @InjectMocks
    private PatientController patientController;

    @Mock
    private PatientServicePort patientServicePort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void testRegisterPatient() throws Exception {
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setName("Alice");
        patientDTO.setCpf("12345678901");
        patientDTO.setEmail("alice@email.com");
        patientDTO.setPassword("senha");

        mockMvc.perform(post("/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDTO)))
                .andExpect(status().isOk());

        verify(patientServicePort, times(1)).registerPatient(any(PatientDTO.class));
    }

    @Test
    void testFindPatientByCpf() throws Exception {
        String cpf = "12345678901";
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setCpf(cpf);
        patientDTO.setName("Alice");

        when(patientServicePort.findPatientByCpf(cpf)).thenReturn(patientDTO);

        mockMvc.perform(get("/patient/{cpf}", cpf)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value(cpf))
                .andExpect(jsonPath("$.name").value("Alice"));
    }
}
