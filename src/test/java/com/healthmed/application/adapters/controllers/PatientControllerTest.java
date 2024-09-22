package com.healthmed.application.adapters.controllers;

import com.healthmed.domain.dtos.patient.PatientDTO;
import com.healthmed.domain.ports.interfaces.PatientServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled("Teste desabilitado temporariamente")
class PatientControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private PatientController patientController;

    @Mock
    private PatientServicePort patientServicePort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
    }

    @Test
    void testInsertProduct() throws Exception {

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Product1\", \"sku\": \"SKU123\", \"price\": 100.0 }"))
                .andExpect(status().isOk());

        verify(patientServicePort, times(1)).registerPatient(any(PatientDTO.class));
    }

    @Test
    void testGetProducts() throws Exception {
        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}
