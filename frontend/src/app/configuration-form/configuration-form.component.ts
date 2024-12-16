import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ApiService } from '../services/api.service';

/**
 * Component for the simulation configuration form.
 */
@Component({
  selector: 'app-configuration-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './configuration-form.component.html',
  styleUrls: ['./configuration-form.component.css']
})
export class ConfigurationFormComponent implements OnInit {
  configForm!: FormGroup;

  /**
   * Event emitted when the simulation configuration is successfully submitted.
   */
  @Output() simulationConfigured = new EventEmitter<void>();

  /**
   * Creates an instance of ConfigurationFormComponent.
   * @param fb The FormBuilder service for creating form groups.
   * @param apiService The ApiService for making API requests.
   */
  constructor(private fb: FormBuilder, private apiService: ApiService) {}

  /**
   * Initializes the component and creates the configuration form.
   */
  ngOnInit() {
    this.configForm = this.fb.group({
      totalTickets: [100, Validators.required],
      ticketReleaseRate: [100, Validators.required],
      customerRetrievalRate: [500, Validators.required],
      maxTicketCapacity: [10, Validators.required]
    });
  }

  /**
   * Handles form submission.
   * Sends the configuration data to the backend via the ApiService.
   * Emits the `simulationConfigured` event when the configuration is successfully submitted.
   */
  onSubmit() {
    if (this.configForm.valid) {
      this.apiService.startSimulation(this.configForm.value).subscribe({
        next: (response) => {
          console.log('Simulation configuration submitted:', response);
          this.simulationConfigured.emit(); // Emit event to notify configuration is ready
        },
        error: (error) => {
          console.error('Error submitting simulation configuration:', error);
          alert('Failed to start simulation. Please check the console for details.');
        }
      });
    } else {
      console.log('Form is invalid');
    }
  }
}
