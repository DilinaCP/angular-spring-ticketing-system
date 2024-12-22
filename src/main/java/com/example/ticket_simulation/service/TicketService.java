package com.example.ticket_simulation.service;

import com.example.ticket_simulation.model.Configuration;
import com.example.ticket_simulation.model.Tickets;
import com.example.ticket_simulation.util.SaveConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Service for managing the ticket simulation.
 */
@Service
public class TicketService {

    @Autowired
    private TicketPool ticketPool;

    @Autowired
    private LoggerService loggerService;

    private Configuration currentConfig;
    private List<Thread> activeThreads = new ArrayList<>();
    private AtomicBoolean simulationRunning = new AtomicBoolean(false);
    private AtomicBoolean simulationStarted = new AtomicBoolean(false);

    /**
     * Starts the simulation with the given configuration.
     *
     * @param config The simulation configuration.
     */
    public void startSimulation(Configuration config) {
        if (simulationStarted.get()) {
            loggerService.log("Simulation is already configured.");
            return;
        }

        this.currentConfig = config;
        ticketPool.initialize(config.getMaxTicketCapacity());
        loggerService.clearLogs();
        activeThreads.clear();

        loggerService.log("Simulation configuration: " +
                "Total Tickets = " + config.getTotalTickets() +
                ", Ticket Release Rate = " + config.getTicketReleaseRate() +
                ", Customer Retrieval Rate = " + config.getCustomerRetrievalRate() +
                ", Max Ticket Capacity = " + config.getMaxTicketCapacity());

        SaveConfiguration.saveAsJson(config);
        SaveConfiguration.saveAsText(config);
        loggerService.log("Configuration saved.");
        simulationStarted.set(true);
    }

    /**
     * Runs the simulation, starting the vendor and customer threads.
     */
    public void runSimulation() {
        if (simulationRunning.get()) {
            loggerService.log("Simulation is already running.");
            return;
        }
        this.simulationRunning.set(true);
        // Start vendor threads
        for (int i = 1; i <= 10; i++) {
            Thread vendorThread = new Thread(createVendorRunnable(i), "Vendor " + i);
            activeThreads.add(vendorThread);
            vendorThread.start();
        }

        // Start customer threads
        for (int i = 1; i <= 10; i++) {
            Thread customerThread = new Thread(createCustomerRunnable(i), "Customer " + i);
            activeThreads.add(customerThread);
            customerThread.start();
        }
    }

    /**
     * Creates a Runnable for a vendor thread.
     *
     * @param vendorId The ID of the vendor.
     * @return A Runnable for the vendor thread.
     */
    private Runnable createVendorRunnable(int vendorId) {
        return () -> {
            int ticketIdCounter = (vendorId - 1) * currentConfig.getTotalTickets() / 10 + 1;
            for (int i = 0; i < currentConfig.getTotalTickets() / 10; i++) {
                if (!simulationRunning.get())
                    break;
                Tickets ticket = new Tickets(ticketIdCounter++, "Event", "Location", 100.0);
                ticketPool.addTicket(ticket);
                loggerService.log("Vendor " + vendorId + " added " + ticket + " [Event: "
                        + ticket.getEvent() + ", Location: " + ticket.getLocation() + ", Price: $"
                        + ticket.getPrice() + "]");
                sleepFor(currentConfig.getTicketReleaseRate());
            }
        };
    }

    /**
     * Creates a Runnable for a customer thread.
     *
     * @param customerId The ID of the customer.
     * @return A Runnable for the customer thread.
     */
    private Runnable createCustomerRunnable(int customerId) {
        return () -> {
            for (int i = 0; i < currentConfig.getTotalTickets() / 10; i++) {
                if (!simulationRunning.get())
                    break;
                Tickets retrievedTicket = ticketPool.retrieveTicket();
                if (retrievedTicket != null) {
                    loggerService.log("Customer " + customerId + " retrieved " + retrievedTicket + " [Event: "
                            + retrievedTicket.getEvent() + ", Location: " + retrievedTicket.getLocation()
                            + ", Price: $" + retrievedTicket.getPrice() + "]");
                }
                sleepFor(currentConfig.getCustomerRetrievalRate());
            }
        };
    }

    /**
     * Sleeps for the specified duration.
     *
     * @param duration The duration to sleep in milliseconds.
     */
    private void sleepFor(int duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Stops the simulation.
     */
    public void stopSimulation() {
        if (!simulationRunning.get()) {
            loggerService.log("Simulation is not running.");
            return;
        }
        // Interrupt and wait for all active threads to finish
        for (Thread thread : activeThreads) {
            if (thread.isAlive()) {
                thread.interrupt();
            }
        }

        activeThreads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                loggerService.log("Interrupted while waiting for thread " + thread.getName() + " to finish.");
            }
        });

        simulationRunning.set(false);
        loggerService.log("Simulation stopped.");
    }

    /**
     * Resets the simulation.
     */
    public void resetSimulation() {
        stopSimulation();
        simulationStarted.set(false);
        ticketPool.initialize(0);
        loggerService.clearLogs();
        loggerService.log("Simulation reset.");
    }

    /**
     * Gets the number of available tickets.
     *
     * @return The number of available tickets.
     */
    public int getTicketsAvailable() {
        return ticketPool.getTicketsAvailable();
    }

    /**
     * Gets the number of sold tickets.
     *
     * @return The number of sold tickets.
     */
    public int getTicketsSold() {
        return ticketPool.getTicketsSold();
    }
}