package com.healthmed.application.adapters.controllers;

import com.healthmed.domain.dtos.AuthDTO;
import com.healthmed.domain.ports.interfaces.AuthServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthServicePort authServicePort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void testLoginSuccess() throws Exception {
        String email = "test@example.com";
        String password = "password";
        AuthDTO authDTO = new AuthDTO("sampleAccessToken");

        when(authServicePort.login(email, password)).thenReturn(authDTO);

        mockMvc.perform(post("/auth/login")
                        .param("email", email)
                        .param("password", password)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("sampleAccessToken"));
    }
}
