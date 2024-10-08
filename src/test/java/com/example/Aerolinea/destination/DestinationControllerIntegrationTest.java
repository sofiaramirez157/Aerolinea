package com.example.Aerolinea.destination;

import com.example.Aerolinea.controller.DestinationController;
import com.example.Aerolinea.exceptions.DestinationNotFoundException;
import com.example.Aerolinea.model.Destination;
import com.example.Aerolinea.service.DestinationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DestinationControllerIntegrationTest {

    @Mock
    private DestinationService destinationService;

    @InjectMocks
    private DestinationController destinationController;

    private MockMvc mockMvc;
    private Destination destination1;
    private Destination destination2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(destinationController).build();

        destination1 = new Destination();
        destination1.setId(1L);
        destination1.setCountry("France");
        destination1.setCode("FR"); // Assuming you want to set a code

        destination2 = new Destination();
        destination2.setId(2L);
        destination2.setCountry("Japan");
        destination2.setCode("JP"); // Assuming you want to set a code
    }

    @Test
    public void createDestination() throws Exception {
        when(destinationService.createDestination(any(Destination.class))).thenReturn(destination1);

        ObjectMapper objectMapper = new ObjectMapper();
        String destinationJson = objectMapper.writeValueAsString(destination1);

        mockMvc.perform(post("/api/destination/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(destinationJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.country").value("France")) // Updated to check country
                .andExpect(jsonPath("$.code").value("FR")); // Check code
    }

    @Test
    public void getAllDestinations() throws Exception {
        List<Destination> destinationList = Arrays.asList(destination1, destination2);

        when(destinationService.getAllDestination()).thenReturn(destinationList);

        mockMvc.perform(get("/api/destination/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].country").value("France")) // Updated to check country
                .andExpect(jsonPath("$[1].country").value("Japan")) // Updated to check country
                .andExpect(jsonPath("$[0].code").value("FR")) // Check code
                .andExpect(jsonPath("$[1].code").value("JP")); // Check code
    }

    @Test
    public void getDestinationById() throws Exception {
        when(destinationService.getDestinationById(1L)).thenReturn(destination1);

        mockMvc.perform(get("/api/destination/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.country").value("France")) // Updated to check country
                .andExpect(jsonPath("$.code").value("FR")); // Check code
    }

    @Test
    public void updateDestination() throws Exception {
        when(destinationService.updateDestination(any(Destination.class), any(Long.class))).thenReturn(destination1);

        ObjectMapper objectMapper = new ObjectMapper();
        String destinationJson = objectMapper.writeValueAsString(destination1);

        mockMvc.perform(put("/api/destination/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(destinationJson))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteDestination() throws Exception {
        when(destinationService.deleteDestination(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/destination/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Destination with ID 1 was deleted."));
    }

    @Test
    public void deleteDestinationNotFound() throws Exception {
        String errorMessage = "Destination not found with ID: 1";
        when(destinationService.deleteDestination(1L)).thenThrow(new DestinationNotFoundException(errorMessage));

        mockMvc.perform(delete("/api/destination/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value(errorMessage))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }
}