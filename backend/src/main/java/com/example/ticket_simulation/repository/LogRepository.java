package com.example.ticket_simulation.repository;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for storing and retrieving log messages.
 */
@Repository
public class LogRepository {

    private final List<String> logs = new ArrayList<>();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Adds a log message to the repository.
     *
     * @param message The message to log.
     */
    public synchronized void addLog(String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        logs.add(timestamp + " - " + message);
    }

    /**
     * Retrieves all log messages from the repository.
     *
     * @return A list of log messages.
     */
    public synchronized List<String> getLogs() {
        return new ArrayList<>(logs);
    }

    /**
     * Clears all log messages from the repository.
     */
    public synchronized void clearLogs() {
        logs.clear();
    }
}