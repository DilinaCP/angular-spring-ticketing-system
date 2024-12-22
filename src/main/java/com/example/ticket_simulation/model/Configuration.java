package com.example.ticket_simulation.model;

/**
 * Represents the configuration for the ticket simulation.
 */
public class Configuration {
    private int maxTicketCapacity;
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;

    /**
     * Default constructor for Configuration.
     */
    public Configuration() {
    }

    /**
     * Constructs a Configuration object with the specified parameters.
     *
     * @param totalTickets         The total number of tickets.
     * @param ticketReleaseRate    The rate at which tickets are released (in milliseconds).
     * @param customerRetrievalRate The rate at which customers retrieve tickets (in milliseconds).
     * @param maxTicketCapacity    The maximum ticket capacity.
     */
    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    /**
     * Gets the maximum ticket capacity.
     *
     * @return The maximum ticket capacity.
     */
    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    /**
     * Sets the maximum ticket capacity.
     *
     * @param maxTicketCapacity The maximum ticket capacity to set.
     */
    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    /**
     * Gets the total number of tickets.
     *
     * @return The total number of tickets.
     */
    public int getTotalTickets() {
        return totalTickets;
    }

    /**
     * Sets the total number of tickets.
     *
     * @param totalTickets The total number of tickets to set.
     */
    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    /**
     * Gets the ticket release rate.
     *
     * @return The ticket release rate.
     */
    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    /**
     * Sets the ticket release rate.
     *
     * @param ticketReleaseRate The ticket release rate to set.
     */
    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    /**
     * Gets the customer retrieval rate.
     *
     * @return The customer retrieval rate.
     */
    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    /**
     * Sets the customer retrieval rate.
     *
     * @param customerRetrievalRate The customer retrieval rate to set.
     */
    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }
}