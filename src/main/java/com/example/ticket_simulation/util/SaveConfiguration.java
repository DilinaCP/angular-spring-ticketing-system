package com.example.ticket_simulation.util;

import com.example.ticket_simulation.model.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Utility class for saving the simulation configuration to a file.
 */
public class SaveConfiguration {

    /**
     * Saves the configuration as a JSON file.
     *
     * @param config The configuration to save.
     */
    public static void saveAsJson(Configuration config) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File("configuration.json"), config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the configuration as a text file.
     *
     * @param config The configuration to save.
     */
    public static void saveAsText(Configuration config) {
        try (FileWriter writer = new FileWriter("configuration.txt")) {
            writer.write("Total Tickets: " + config.getTotalTickets() + "\n");
            writer.write("Ticket Release Rate: " + config.getTicketReleaseRate() + "\n");
            writer.write("Customer Retrieval Rate: " + config.getCustomerRetrievalRate() + "\n");
            writer.write("Max Ticket Capacity: " + config.getMaxTicketCapacity() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}