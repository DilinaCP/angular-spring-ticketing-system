import { Component, OnInit } from '@angular/core';
import { ApiService } from '../services/api.service';
import { CommonModule } from '@angular/common';

/**
 * Component for the simulation control panel.
 */
@Component({
  selector: 'app-control-panel',
  templateUrl: './control-panel.component.html',
  styleUrls: ['./control-panel.component.css'],
  standalone: true,
  imports: [CommonModule],
})
export class ControlPanelComponent implements OnInit {
  simulationRunning = false;
  simulationConfigured = false;

  /**
   * Creates an instance of ControlPanelComponent.
   * @param apiService The ApiService for making API requests.
   */
  constructor(private apiService: ApiService) {}

  /**
   * Initializes the component.
   * (No specific initialization logic in this component)
   */
  ngOnInit(): void {}

  /**
   * Called when the simulation is configured.
   * Sets the `simulationConfigured` flag to `true`.
   */
  onSimulationConfigured() {
    this.simulationConfigured = true;
  }

  /**
   * Starts the simulation by calling the `runSimulation` method of the `ApiService`.
   * Sets the `simulationRunning` flag to `true` on success.
   */
  startSimulation() {
    this.apiService.runSimulation().subscribe({
      next: (response: any) => {
        this.simulationRunning = true;
        console.log('Simulation started response:', response);
      },
      error: (error: any) => {
        console.error('Error starting simulation:', error);
      },
    });
  }

  /**
   * Stops the simulation by calling the `stopSimulation` method of the `ApiService`.
   * Sets the `simulationRunning` flag to `false` on success.
   */
  stopSimulation() {
    this.apiService.stopSimulation().subscribe({
      next: (response) => {
        this.simulationRunning = false;
        console.log('Simulation stopped response:', response);
      },
      error: (error) => {
        console.error('Error stopping simulation:', error);
      },
    });
  }

  /**
   * Resets the simulation by calling the `resetSimulation` method of the `ApiService`.
   * Sets both `simulationRunning` and `simulationConfigured` flags to `false` on success.
   */
  resetSimulation() {
    this.apiService.resetSimulation().subscribe({
      next: (response) => {
        this.simulationRunning = false;
        this.simulationConfigured = false;
        console.log('Simulation reset response:', response);
      },
      error: (error) => {
        console.error('Error resetting simulation:', error);
      },
    });
  }
}
