package com.example.ticket_simulation.service;

import com.example.ticket_simulation.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for managing logs.
 */
@Service
public class LoggerService {

    @Autowired
    private LogRepository logRepository;

    /**
     * Logs a message.
     *
     * @param message The message to log.
     */
    public void log(String message) {
        logRepository.addLog(message);
    }

    /**
     * Retrieves all log messages.
     *
     * @return A list of log messages.
     */
    public List<String> getLogs() {
        return logRepository.getLogs();
    }

    /**
     * Clears all log messages.
     */
    public void clearLogs() {
        logRepository.clearLogs();
    }
}