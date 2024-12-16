import {
  Component,
  OnInit,
  OnDestroy,
  ViewChild,
  ElementRef,
  AfterViewChecked,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { ApiService } from '../services/api.service';
import { interval, Subscription } from 'rxjs';
import { startWith, switchMap } from 'rxjs/operators';

/**
 * Component for displaying log messages.
 */
@Component({
  selector: 'app-log-display',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './log-display.component.html',
  styleUrls: ['./log-display.component.css'],
})
export class LogDisplayComponent implements OnInit, OnDestroy, AfterViewChecked {
  logs: string[] = [];
  private logSubscription?: Subscription;

  /**
   * Reference to the log container element in the template.
   */
  @ViewChild('logContainer') private logContainer!: ElementRef;

  /**
   * Creates an instance of LogDisplayComponent.
   * @param apiService The ApiService for fetching logs.
   */
  constructor(private apiService: ApiService) {}

  /**
   * Initializes the component and starts fetching logs.
   */
  ngOnInit() {
    this.logSubscription = interval(1000)
      .pipe(
        startWith(0),
        switchMap(() => this.apiService.getLogs())
      )
      .subscribe({
        next: (logs) => {
          this.logs = logs;
        },
        error: (error) => {
          console.error('Error fetching logs:', error);
        },
      });
  }

  /**
   * Scrolls to the bottom of the log container after the view has been checked.
   */
  ngAfterViewChecked() {
    this.scrollToBottom();
  }

  /**
   * Unsubscribes from the log subscription when the component is destroyed.
   */
  ngOnDestroy() {
    this.logSubscription?.unsubscribe();
  }

  /**
   * Scrolls to the bottom of the log container.
   */
  private scrollToBottom(): void {
    try {
      this.logContainer.nativeElement.scrollTop =
        this.logContainer.nativeElement.scrollHeight;
    } catch (err) {
      console.error(err);
    }
  }
}
