package com.example.ticket_simulation.model;

/**
 * Represents the configuration for the simulation.
 * This class is currently not used.
 */
public class TicketConfiguration {
    private int duration;
    private int ticketCount;

    /**
     * Gets the duration of the simulation.
     *
     * @return The duration of the simulation.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Sets the duration of the simulation.
     *
     * @param duration The duration to set.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Gets the ticket count.
     *
     * @return The ticket count.
     */
    public int getTicketCount() {
        return ticketCount;
    }

    /**
     * Sets the ticket count.
     *
     * @param ticketCount The ticket count to set.
     */
    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }
}