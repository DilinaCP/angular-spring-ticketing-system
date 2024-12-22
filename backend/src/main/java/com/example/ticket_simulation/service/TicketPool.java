package com.example.ticket_simulation.service;

import com.example.ticket_simulation.model.Tickets;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Represents a pool of tickets for the simulation.
 */
@Component
public class TicketPool {
    private Queue<Tickets> ticketsQueue = new LinkedList<>();
    private int maxTicketCapacity;
    private int ticketsAvailable = 0;
    private int ticketsSold = 0;

    /**
     * Initializes the ticket pool with the specified maximum capacity.
     *
     * @param maxTicketCapacity The maximum capacity of the ticket pool.
     */
    public void initialize(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
        this.ticketsQueue.clear();
        this.ticketsAvailable = 0;
        this.ticketsSold = 0;
    }

    /**
     * Adds a ticket to the pool.
     *
     * @param ticket The ticket to add.
     */
    public synchronized void addTicket(Tickets ticket) {
        while (ticketsQueue.size() >= maxTicketCapacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                // Check interrupted status and exit if interrupted
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }
            }
        }
        ticketsQueue.add(ticket);
        ticketsAvailable++;
        notifyAll();
    }

    /**
     * Retrieves a ticket from the pool.
     *
     * @return The retrieved ticket, or null if the pool is empty.
     */
    public synchronized Tickets retrieveTicket() {
        while (ticketsQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                // Check interrupted status and exit if interrupted
                if (Thread.currentThread().isInterrupted()) {
                    return null;
                }
            }
        }
        Tickets ticket = ticketsQueue.poll();
        ticketsAvailable--;
        ticketsSold++;
        notifyAll();
        return ticket;
    }

    /**
     * Gets the number of available tickets in the pool.
     *
     * @return The number of available tickets.
     */
    public synchronized int getTicketsAvailable() {
        return ticketsAvailable;
    }

    /**
     * Gets the number of sold tickets.
     *
     * @return The number of sold tickets.
     */
    public synchronized int getTicketsSold() {
        return ticketsSold;
    }
}