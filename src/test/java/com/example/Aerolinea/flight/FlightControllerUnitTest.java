package com.example.Aerolinea.flight;

import com.example.Aerolinea.controller.FlightController;
import com.example.Aerolinea.dto.FlightRequestDTO;
import com.example.Aerolinea.dto.FlightResponseDTO;
import com.example.Aerolinea.dto.DestinationDTO;
import com.example.Aerolinea.model.Flight;
import com.example.Aerolinea.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.springframework.http.ResponseEntity;

public class FlightControllerUnitTest {

    @Mock
    private FlightService flightService;

    @InjectMocks
    private FlightController flightController;

    private Flight flight;
    private FlightRequestDTO flightRequestDTO;
    private FlightResponseDTO flightResponseDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        flight = new Flight();
        flight.setId(1L);
        flight.setOrigin("Madrid");
        flight.setDepartureTime(LocalTime.of(8, 15));
        flight.setArrivalTime(LocalTime.of(10, 20));
        flight.setAvailableSeats(60);
        flight.setStatus(true);

        DestinationDTO destinationDTO = new DestinationDTO();
        destinationDTO.setId(1L);
        destinationDTO.setCountry("Spain");
        destinationDTO.setCode("MAD");

        flightRequestDTO = new FlightRequestDTO();
        flightRequestDTO.setOrigin("Madrid");
        flightRequestDTO.setDestinationId(1L);
        flightRequestDTO.setDepartureTime(LocalTime.of(8, 15));
        flightRequestDTO.setArrivalTime(LocalTime.of(10, 20));
        flightRequestDTO.setAvailableSeats(60);
        flightRequestDTO.setStatus(true);

        flightResponseDTO = new FlightResponseDTO(
                flight.getId(),
                flight.getOrigin(),
                destinationDTO,
                flight.getDepartureTime(),
                flight.getArrivalTime(),
                flight.getAvailableSeats(),
                flight.isStatus()
        );
    }

    @Test
    void createFlight() {
        when(flightService.createFlight(any(FlightRequestDTO.class))).thenReturn(flightResponseDTO);

        ResponseEntity<FlightResponseDTO> response = flightController.createFlight(flightRequestDTO);

        assertNotNull(response);
        assertEquals(flightResponseDTO.getId(), response.getBody().getId());
        assertEquals(flightResponseDTO.getOrigin(), response.getBody().getOrigin());
        assertEquals(flightResponseDTO.getDepartureTime(), response.getBody().getDepartureTime());
        assertEquals(flightResponseDTO.getArrivalTime(), response.getBody().getArrivalTime());
        assertEquals(flightResponseDTO.getAvailableSeats(), response.getBody().getAvailableSeats());
        assertEquals(flightResponseDTO.isStatus(), response.getBody().isStatus());
    }

    @Test
    void getAllFlights() {
        when(flightService.getAllFlights()).thenReturn(Collections.singletonList(flightResponseDTO));

        ResponseEntity<List<FlightResponseDTO>> response = flightController.getAllFlights();

        assertNotNull(response);
        assertEquals(1, response.getBody().size());
        assertEquals(flightResponseDTO.getId(), response.getBody().get(0).getId());
        assertEquals(flightResponseDTO.getOrigin(), response.getBody().get(0).getOrigin());
        assertEquals(flightResponseDTO.getDepartureTime(), response.getBody().get(0).getDepartureTime());
        assertEquals(flightResponseDTO.getArrivalTime(), response.getBody().get(0).getArrivalTime());
        assertEquals(flightResponseDTO.getAvailableSeats(), response.getBody().get(0).getAvailableSeats());
        assertEquals(flightResponseDTO.isStatus(), response.getBody().get(0).isStatus());
    }

    @Test
    void getFlightById() {
        when(flightService.getFlightById(1L)).thenReturn(flightResponseDTO);

        ResponseEntity<FlightResponseDTO> response = flightController.getFlightById(1L);

        assertNotNull(response);
        assertEquals(flightResponseDTO.getId(), response.getBody().getId());
        assertEquals(flightResponseDTO.getOrigin(), response.getBody().getOrigin());
        assertEquals(flightResponseDTO.getDepartureTime(), response.getBody().getDepartureTime());
        assertEquals(flightResponseDTO.getArrivalTime(), response.getBody().getArrivalTime());
        assertEquals(flightResponseDTO.getAvailableSeats(), response.getBody().getAvailableSeats());
        assertEquals(flightResponseDTO.isStatus(), response.getBody().isStatus());
    }

    @Test
    void updateFlight() {
        when(flightService.updateFlight(any(FlightRequestDTO.class), any(Long.class))).thenReturn(flightResponseDTO);

        ResponseEntity<FlightResponseDTO> response = flightController.updateFlight(flightRequestDTO, 1L);

        assertNotNull(response);
        assertEquals(flightResponseDTO.getId(), response.getBody().getId());
        assertEquals(flightResponseDTO.getOrigin(), response.getBody().getOrigin());
        assertEquals(flightResponseDTO.getDepartureTime(), response.getBody().getDepartureTime());
        assertEquals(flightResponseDTO.getArrivalTime(), response.getBody().getArrivalTime());
        assertEquals(flightResponseDTO.getAvailableSeats(), response.getBody().getAvailableSeats());
        assertEquals(flightResponseDTO.isStatus(), response.getBody().isStatus());
    }

    @Test
    void deleteFlightById() {
        doNothing().when(flightService).deleteFlightById(1L);

        ResponseEntity<String> response = flightController.deleteFlightById(1L);

        assertEquals("Flight with ID 1 was deleted.", response.getBody());
    }
}