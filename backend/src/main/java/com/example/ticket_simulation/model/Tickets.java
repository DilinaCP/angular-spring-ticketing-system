package com.example.ticket_simulation.model;

/**
 * Represents a ticket in the simulation.
 */
public class Tickets {
    private final int id;
    private final String event;
    private final String location;
    private final double price;

    /**
     * Constructs a Tickets object with the specified ID, event, location, and price.
     *
     * @param id       The unique ID of the ticket.
     * @param event    The event associated with the ticket.
     * @param location The location of the event.
     * @param price    The price of the ticket.
     */
    public Tickets(int id, String event, String location, double price) {
        this.id = id;
        this.event = event;
        this.location = location;
        this.price = price;
    }

    /**
     * Gets the ID of the ticket.
     *
     * @return The ticket ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the event associated with the ticket.
     *
     * @return The event.
     */
    public String getEvent() {
        return event;
    }

    /**
     * Gets the location of the event.
     *
     * @return The location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets the price of the ticket.
     *
     * @return The price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns a string representation of the ticket.
     *
     * @return A string representation of the ticket.
     */
    @Override
    public String toString() {
        return "Ticket ID " + id;
    }
}