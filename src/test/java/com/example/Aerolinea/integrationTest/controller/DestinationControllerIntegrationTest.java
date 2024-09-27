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

import java.util.Arrays;
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
            Destination destination1 = new Destination(1, "Paris", "France");
            Destination destination2 = new Destination(2, "Rome", "Italy");
            List<Destination> destinationList = Arrays.asList(destination1, destination2);

        when(destinationService.getAllDestination()).thenReturn(destinationList);

        mockMvc.perform(get("/api/destination/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Paris"))
                .andExpect(jsonPath("$[0].country").value("France"))
                .andExpect(jsonPath("$[1].name").value("Rome"))
                .andExpect(jsonPath("$[1].country").value("Italy"));
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
            when(destinationService.updateDestination(destination1, 1)).thenReturn(destination1);

        ObjectMapper objectMapper = new ObjectMapper();
        String destinationJson = objectMapper.writeValueAsString(destination1);

            mockMvc.perform(put("/api/destination/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(destinationJson))
                    .andExpect(status().isOk());
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
