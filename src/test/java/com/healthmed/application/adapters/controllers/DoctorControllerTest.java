package com.healthmed.application.adapters.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthmed.domain.dtos.doctor.DoctorDTO;
import com.healthmed.domain.ports.interfaces.DoctorServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DoctorControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @InjectMocks
    private DoctorController doctorController;

    @Mock
    private DoctorServicePort doctorServicePort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(doctorController).build();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void testRegisterDoctor() throws Exception {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setName("John");
        doctorDTO.setCpf("123456789001");
        doctorDTO.setCrm("cmr");
        doctorDTO.setEmail("email@email.com");
        doctorDTO.setPassword("senha");

        mockMvc.perform(post("/doctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctorDTO)))
                .andExpect(status().isOk());

        verify(doctorServicePort, times(1)).registerDoctor(any(DoctorDTO.class));
    }

    @Test
    void testFindDoctorByCpf() throws Exception {
        String cpf = "12345678900";
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setCpf(cpf);
        doctorDTO.setName("John");

        when(doctorServicePort.findDoctorByCpf(cpf)).thenReturn(doctorDTO);

        mockMvc.perform(get("/doctor/{cpf}", cpf)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value(cpf))
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void testFindAllDoctors() throws Exception {
        DoctorDTO doctorDTO1 = new DoctorDTO();
        doctorDTO1.setCpf("12345678900");
        doctorDTO1.setName("John");

        DoctorDTO doctorDTO2 = new DoctorDTO();
        doctorDTO2.setCpf("09876543210");
        doctorDTO2.setName("Jane");

        when(doctorServicePort.findAll()).thenReturn(List.of(doctorDTO1, doctorDTO2));

        mockMvc.perform(get("/doctor")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cpf").value("12345678900"))
                .andExpect(jsonPath("$[1].cpf").value("09876543210"));
    }
}
