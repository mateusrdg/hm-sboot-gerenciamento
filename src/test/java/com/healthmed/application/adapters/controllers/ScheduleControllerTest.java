package com.healthmed.application.adapters.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthmed.domain.dtos.schedule.AppointmentScheduleDTO;
import com.healthmed.domain.dtos.schedule.AppointmentScheduleRequestDTO;
import com.healthmed.domain.ports.interfaces.ScheduleServicePort;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ScheduleControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @InjectMocks
    private ScheduleController scheduleController;

    @Mock
    private ScheduleServicePort scheduleServicePort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(scheduleController).build();
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateScheduleSuccess() throws Exception {
        String doctorCpf = "12345678901";
        List<AppointmentScheduleRequestDTO> schedules = List.of(new AppointmentScheduleRequestDTO());

        mockMvc.perform(post("/schedule/doctors/{doctorCpf}/schedule", doctorCpf)
                        .content(objectMapper.writeValueAsString(schedules))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Schedule created successfully"));

        verify(scheduleServicePort, times(1)).createSchedule(eq(doctorCpf), any(List.class));
    }

    @Test
    void testUpdateScheduleSuccess() throws Exception {
        String doctorCpf = "12345678901";
        Long scheduleId = 1L;
        AppointmentScheduleRequestDTO scheduleRequest = new AppointmentScheduleRequestDTO();

        mockMvc.perform(put("/schedule/doctors/{doctorCpf}/schedule/{scheduleId}", doctorCpf, scheduleId)
                        .content(objectMapper.writeValueAsString(scheduleRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Schedule updated successfully"));

        verify(scheduleServicePort, times(1)).updateSchedule(eq(doctorCpf), eq(scheduleId), any(AppointmentScheduleRequestDTO.class));
    }

    @Test
    void testDeleteScheduleSuccess() throws Exception {
        String doctorCpf = "12345678901";
        Long scheduleId = 1L;

        mockMvc.perform(delete("/schedule/doctors/{doctorCpf}/schedule/{scheduleId}", doctorCpf, scheduleId))
                .andExpect(status().isOk())
                .andExpect(content().string("Schedule deleted successfully"));

        verify(scheduleServicePort, times(1)).deleteSchedule(eq(doctorCpf), eq(scheduleId));
    }

    @Test
    void testGetAvailableSchedulesSuccess() throws Exception {
        String doctorCpf = "12345678901";
        AppointmentScheduleDTO scheduleDTO = new AppointmentScheduleDTO();
        List<AppointmentScheduleDTO> schedules = List.of(scheduleDTO);

        when(scheduleServicePort.getAvailableSchedules(doctorCpf)).thenReturn(schedules);

        mockMvc.perform(get("/schedule/doctors/{doctorCpf}/schedules", doctorCpf))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").isNotEmpty());

        verify(scheduleServicePort, times(1)).getAvailableSchedules(eq(doctorCpf));
    }

    @Test
    void testBookAppointmentSuccess() throws Exception {
        String doctorCpf = "12345678901";
        Long scheduleId = 1L;
        String patientCpf = "patientCpf";

        mockMvc.perform(post("/schedule/doctors/{doctorCpf}/schedules/{scheduleId}/book", doctorCpf, scheduleId)
                        .content(patientCpf)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Appointment booked successfully"));

        verify(scheduleServicePort, times(1)).bookAppointment(eq(doctorCpf), eq(scheduleId), eq(patientCpf));
    }
}
