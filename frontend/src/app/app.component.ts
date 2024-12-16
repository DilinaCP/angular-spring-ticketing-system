import { Component, ViewChild, AfterViewInit } from '@angular/core';
import { ConfigurationFormComponent } from './configuration-form/configuration-form.component';
import { ControlPanelComponent } from './control-panel/control-panel.component';
import { LogDisplayComponent } from './log-display/log-display.component';
import { TicketDisplayComponent } from './ticket-display/ticket-display.component';

/**
 * The main application component.
 */
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  standalone: true,
  imports: [
    ConfigurationFormComponent,
    ControlPanelComponent,
    LogDisplayComponent,
    TicketDisplayComponent,
  ],
})
export class AppComponent implements AfterViewInit {
  title = 'ticket-system-frontend';
  refreshTrigger: any;

  /**
   * Reference to the ControlPanelComponent.
   */
  @ViewChild(ControlPanelComponent) controlPanelComponent!: ControlPanelComponent;

  constructor() {}

  /**
   * Called after Angular has fully initialized a component's view.
   * Initializes the ControlPanelComponent after the view is ready.
   */
  ngAfterViewInit() {}

  /**
   * Called when the simulation is configured.
   * Triggers a refresh of the ticket display and updates the control panel.
   */
  onSimulationConfigured() {
    this.refreshTrigger = new Date(); // Update ticket display
    if (this.controlPanelComponent) {
      this.controlPanelComponent.onSimulationConfigured(); // Update control panel
    }
  }
}
