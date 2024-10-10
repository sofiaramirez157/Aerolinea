package com.example.Aerolinea.reservation;

import com.example.Aerolinea.model.*;
import com.example.Aerolinea.repository.IDestinationRepository;
import com.example.Aerolinea.repository.IFlightRepository;
import com.example.Aerolinea.repository.IUserRepository;
import com.example.Aerolinea.repository.IReservationRepository;
import com.example.Aerolinea.testConfig.TestSecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public class ReservationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IDestinationRepository destinationRepository;

    @Autowired
    private IFlightRepository flightRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IReservationRepository reservationRepository;

    private Reservation testReservation;
    private Destination destination;
    private User savedUser;
    private Flight savedFlight;

    @BeforeEach
    void setUp() {
        destination = new Destination();
        destination.setCountry("USA");
        destination.setCode("US");
        destination = destinationRepository.save(destination);

        User user = new User();
        user.setUsername("Norbert");
        user.setEmail("example@example.com");
        user.setRole(ERole.USER);
        savedUser = userRepository.save(user);

        Flight flight = new Flight();
        flight.setOrigin("Brasil");
        flight.setDestination(destination);
        flight.setDepartureTime(LocalTime.now());
        flight.setArrivalTime(LocalTime.NOON);
        flight.setAvailableSeats(10);
        flight.setStatus(true);
        savedFlight = flightRepository.save(flight);

        testReservation = new Reservation();
        testReservation.setReservationDate(LocalDateTime.now());
        testReservation.setStatus(true);
        testReservation.setUser(savedUser);

        Set<Flight> flights = new HashSet<>();
        flights.add(savedFlight);
        testReservation.setFlights(flights);

        reservationRepository.save(testReservation);
    }

    @Test
    void createReservationTest() throws Exception {
        String reservationJson = objectMapper.writeValueAsString(testReservation);

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    void getAllReservationsTest() throws Exception {
        mockMvc.perform(get("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void getReservationByIdTest() throws Exception {
        String reservationJson = objectMapper.writeValueAsString(testReservation);
        MvcResult result = mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationJson))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Reservation createdReservation = objectMapper.readValue(responseBody, Reservation.class);
        Long reservationId = createdReservation.getId();

        mockMvc.perform(get("/api/reservations/" + reservationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(reservationId));
    }

    @Test
    void updateReservationTest() throws Exception {
        String reservationJson = objectMapper.writeValueAsString(testReservation);
        MvcResult result = mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationJson))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Reservation createdReservation = objectMapper.readValue(responseBody, Reservation.class);
        Long reservationId = createdReservation.getId();

        testReservation.setStatus(false);
        String updatedReservationJson = objectMapper.writeValueAsString(testReservation);

        mockMvc.perform(put("/api/reservations/" + reservationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedReservationJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(reservationId))
                .andExpect(jsonPath("$.status").value(false));
    }

    @Test
    void deleteReservationTest() throws Exception {
        String reservationJson = objectMapper.writeValueAsString(testReservation);
        MvcResult result = mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationJson))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Reservation createdReservation = objectMapper.readValue(responseBody, Reservation.class);
        Long reservationId = createdReservation.getId();

        mockMvc.perform(delete("/api/reservations/" + reservationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}