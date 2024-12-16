import { Component, OnInit, OnDestroy, Input, SimpleChanges, OnChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from '../services/api.service';
import { Subscription } from 'rxjs';

/**
 * Component for displaying ticket information.
 */
@Component({
    selector: 'app-ticket-display',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './ticket-display.component.html',
    styleUrls: ['./ticket-display.component.css']
})
export class TicketDisplayComponent implements OnInit, OnDestroy, OnChanges {
    ticketsAvailable: number = 0;
    ticketsSold: number = 0;
    private ticketSubscription?: Subscription;

    /**
     * Input property to trigger a refresh of the ticket data.
     */
    @Input() refreshTrigger: any;

    /**
     * Creates an instance of TicketDisplayComponent.
     * @param apiService The ApiService for fetching ticket data.
     */
    constructor(private apiService: ApiService) { }

    /**
     * Initializes the component and fetches the initial ticket data.
     */
    ngOnInit() {
      this.fetchTicketData();
    }

    /**
     * Called when the component's input properties change.
     * Refreshes the ticket data if the `refreshTrigger` input changes.
     * @param changes The changes detected in the input properties.
     */
    ngOnChanges(changes: SimpleChanges) {
        if (changes['refreshTrigger'] && !changes['refreshTrigger'].firstChange) {
            this.fetchTicketData();
        }
    }

    /**
     * Unsubscribes from the ticket data subscription when the component is destroyed.
     */
    ngOnDestroy() {
        this.unsubscribeFromTicketData();
    }

    /**
     * Fetches the ticket data from the backend and updates the `ticketsAvailable` and `ticketsSold` properties.
     * Unsubscribes from any existing subscription before fetching new data.
     */
    private fetchTicketData() {
      this.unsubscribeFromTicketData(); // Unsubscribe from any previous subscription
      this.ticketSubscription = this.apiService.getTicketsAvailable().subscribe({
        next: (available) => {
          this.ticketsAvailable = available;
        },
        error: (error) => {
          console.error('Error fetching available tickets:', error);
        }
      });

      this.apiService.getTicketsSold().subscribe({
        next: (sold) => {
          this.ticketsSold = sold;
        },
        error: (error) => {
          console.error('Error fetching sold tickets:', error);
        }
      });
    }

    /**
     * Unsubscribes from the ticket data subscription.
     */
    private unsubscribeFromTicketData() {
        if (this.ticketSubscription) {
            this.ticketSubscription.unsubscribe();
            this.ticketSubscription = undefined;
        }
    }
}
