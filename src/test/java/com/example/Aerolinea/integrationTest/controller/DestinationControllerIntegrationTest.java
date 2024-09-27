package com.example.Aerolinea.integrationTest.controller;

import com.example.Aerolinea.controller.DestinationController;
import com.example.Aerolinea.model.Destination;
import com.example.Aerolinea.service.DestinationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
    private List<Destination> destinationList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(destinationController).build();

        destination1 = new Destination();
        destination1.setId(1);
        destination1.setName("Madrid");
        destination1.setCountry("España");

        destination2 = new Destination();
        destination2.setId(2);
        destination2.setName("Paris");
        destination2.setCountry("Francia");

    }
       @Test
         void createDestinationTest() throws Exception{
           when(destinationService.createDestination(any(Destination.class))).thenReturn(destination1);

           ObjectMapper objectMapper = new ObjectMapper();
           String destinationJson = objectMapper.writeValueAsString(destination1);

           mockMvc.perform(post("/api/destination/")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(destinationJson))
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$.id").value(1))
                   .andExpect(jsonPath("$.name").value("Madrid"))
                   .andExpect(jsonPath("$.country").value("España"));
        }

        @Test
          void getAllDestinationTest() throws Exception{
        when(destinationService.getAllDestination()).thenReturn(destinationList);

        mockMvc.perform(get("/api/destination"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
        }

        @Test
          void getDestinationByIdTest() throws Exception{
        when(destinationService.getDestinationById(1)).thenReturn(Optional.of(destination1));

        mockMvc.perform(get("/api/destination/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
        }

        @Test
          void updateDestinationTest() throws Exception{
        Destination updateDestination = new Destination();
        updateDestination.setId(2);
        updateDestination.setName("Burdeos");

            when(destinationService.updateDestination(updateDestination, 1)).thenReturn(destination2);

        ObjectMapper objectMapper = new ObjectMapper();
        String destinationJson = objectMapper.writeValueAsString(updateDestination);

            mockMvc.perform(put("/api/destination/update/2")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(destinationJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(2))
                    .andExpect(jsonPath("$.name").value("Burdeos"));
        }

        @Test
         void deleteDestinationTest() throws Exception{
            when(destinationService.deleteDestination(1)).thenReturn(true);

            mockMvc.perform(delete("/api/destination/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").value("Destination with id 1 was delete"));

        }

}
