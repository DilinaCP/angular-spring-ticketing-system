package com.example.ticket_simulation.controller;

import com.example.ticket_simulation.model.Configuration;
import com.example.ticket_simulation.service.LoggerService;
import com.example.ticket_simulation.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Controller for managing the ticket simulation.
 */
@RestController
@RequestMapping("/api")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private LoggerService loggerService;

    /**
     * Starts the simulation with the provided configuration.
     *
     * @param config The simulation configuration.
     * @return A ResponseEntity with a success or error message.
     */
    @PostMapping("/start")
    public ResponseEntity<Map<String, String>> startSimulation(@RequestBody Configuration config) {
        try {
            ticketService.startSimulation(config);
            // Return a JSON object with a success message
            return ResponseEntity.ok(Collections.singletonMap("message", "Simulation started"));
        } catch (Exception e) {
            loggerService.log("Error starting simulation: " + e.getMessage());
            // Return a JSON object with an error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error starting simulation"));
        }
    }

    /**
     * Runs the simulation.
     *
     * @return A ResponseEntity with a success or error message.
     */
    @PostMapping("/run")
    public ResponseEntity<Map<String, String>> runSimulation() {
        try {
            ticketService.runSimulation();
            return ResponseEntity.ok(Collections.singletonMap("message", "Simulation running"));
        } catch (Exception e) {
            loggerService.log("Error running simulation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error running simulation"));
        }
    }

    /**
     * Stops the simulation.
     *
     * @return A ResponseEntity with a success or error message.
     */
    @PostMapping("/stop")
    public ResponseEntity<Map<String, String>> stopSimulation() {
        try {
            ticketService.stopSimulation();
            return ResponseEntity.ok(Collections.singletonMap("message", "Simulation stopped"));
        } catch (Exception e) {
            loggerService.log("Error stopping simulation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error stopping simulation"));
        }
    }

    /**
     * Resets the simulation.
     *
     * @return A ResponseEntity with a success or error message.
     */
    @PostMapping("/reset")
    public ResponseEntity<Map<String, String>> resetSimulation() {
        try {
            ticketService.resetSimulation();
            return ResponseEntity.ok(Collections.singletonMap("message", "Simulation reset"));
        } catch (Exception e) {
            loggerService.log("Error resetting simulation: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error resetting simulation"));
        }
    }

    /**
     * Retrieves the logs.
     *
     * @return A ResponseEntity containing the list of logs or a NO_CONTENT status if no logs are available.
     */
    @GetMapping("/logs")
    public ResponseEntity<List<String>> getLogs() {
        List<String> logs = loggerService.getLogs();
        if (logs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(logs);
        }
        return ResponseEntity.ok(logs);
    }

    /**
     * Retrieves the number of available tickets.
     *
     * @return A ResponseEntity containing the number of available tickets.
     */
    @GetMapping("/tickets/available")
    public ResponseEntity<Integer> getTicketsAvailable() {
        int available = ticketService.getTicketsAvailable();
        return ResponseEntity.ok(available);
    }

    /**
     * Retrieves the number of sold tickets.
     *
     * @return A ResponseEntity containing the number of sold tickets.
     */
    @GetMapping("/tickets/sold")
    public ResponseEntity<Integer> getTicketsSold() {
        int sold = ticketService.getTicketsSold();
        return ResponseEntity.ok(sold);
    }
}