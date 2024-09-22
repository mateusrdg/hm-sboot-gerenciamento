package com.healthmed.application.adapters.controllers.exception;

import com.healthmed.application.adapters.controllers.DoctorController;
import com.healthmed.application.adapters.controllers.exception.handler.CustomExceptionHandler;
import com.healthmed.domain.dtos.doctor.DoctorDTO;
import com.healthmed.domain.ports.interfaces.DoctorServicePort;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled("Teste desabilitado temporariamente")
class CustomExceptionHandlerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private DoctorController doctorController;

    @Mock
    private DoctorServicePort doctorServicePort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(doctorController)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
    }

    @Test
    void testHandleBadRequestException() throws Exception {
        doThrow(new BadRequestException("Bad Request"))
                .when(doctorServicePort)
                .registerDoctor(any(DoctorDTO.class));


        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"John\", \"cpf\": \"12345678900\" }"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Bad Request"));
    }

    @Test
    void testHandleNotFoundException() throws Exception {
        when(doctorServicePort.findDoctorByCpf(any(String.class)))
                .thenThrow(new NotFoundException("Customer not found"));

        mockMvc.perform(get("/customers/12345678900")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Customer not found"));
    }

    @Test
    void testHandleMethodArgumentNotValidException() throws Exception {
        // Simulate validation failure
        DoctorDTO invalidCustomer = new DoctorDTO();
        invalidCustomer.setName(""); // Nome vazio para simular falha de validação
        invalidCustomer.setCpf("12345678900");

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"\", \"cpf\": \"12345678900\" }"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("O campo 'nome' não pode estar em branco"));
    }
}
