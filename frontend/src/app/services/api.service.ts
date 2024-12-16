import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { environment } from '../../enviroment/enviroment';
import { catchError, map } from 'rxjs/operators';

/**
 * Service for making API requests to the backend.
 */
@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private apiUrl = environment.apiUrl;

  /**
   * Creates an instance of ApiService.
   * @param http The HttpClient for making HTTP requests.
   */
  constructor(private http: HttpClient) { }

  /**
   * Starts the simulation with the given configuration.
   *
   * @param config The simulation configuration.
   * @returns An Observable that emits the response from the backend.
   */
  startSimulation(config: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/start`, config)
      .pipe(
        map(response => {
          // Check if the response has a message property
          if (response && response.message) {
            console.log(response.message); // Log the success message
          }
          return response;
        }),
        catchError(this.handleError)
      );
  }

  /**
   * Runs the simulation.
   *
   * @returns An Observable that emits the response from the backend.
   */
  runSimulation(): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/run`, {})
      .pipe(
        map(response => {
          // Check if the response has a message property
          if (response && response.message) {
            console.log(response.message); // Log the success message
          }
          return response;
        }),
        catchError(this.handleError)
      );
  }

  /**
   * Stops the simulation.
   *
   * @returns An Observable that emits the response from the backend.
   */
  stopSimulation(): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/stop`, {})
      .pipe(
        map(response => {
          // Check if the response has a message property
          if (response && response.message) {
            console.log(response.message); // Log the success message
          }
          return response;
        }),
        catchError(this.handleError)
      );
  }

  /**
   * Resets the simulation.
   *
   * @returns An Observable that emits the response from the backend.
   */
  resetSimulation(): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/reset`, {})
      .pipe(
        map(response => {
          // Check if the response has a message property
          if (response && response.message) {
            console.log(response.message); // Log the success message
          }
          return response;
        }),
        catchError(this.handleError)
      );
  }

  /**
   * Retrieves the logs from the backend.
   *
   * @returns An Observable that emits an array of log messages.
   */
  getLogs(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/logs`).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 200 && !error.ok) {
          console.error('Server returned 200 OK but with error flag:', error);
          return throwError(() => new Error('Server returned an error even with a 200 status code.'));
        }
        let errorMessage = 'An unknown error occurred!';
        if (error.error instanceof ErrorEvent) {
          errorMessage = `Error: ${error.error.message}`;
        } else {
          errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
        }
        console.error(errorMessage);
        return throwError(() => new Error(errorMessage));
      })
    );
  }

  /**
   * Retrieves the number of available tickets from the backend.
   *
   * @returns An Observable that emits the number of available tickets.
   */
  getTicketsAvailable(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/tickets/available`).pipe(
      catchError(this.handleError)
    );
  }

  /**
   * Retrieves the number of sold tickets from the backend.
   *
   * @returns An Observable that emits the number of sold tickets.
   */
  getTicketsSold(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/tickets/sold`).pipe(
      catchError(this.handleError)
    );
  }

  /**
   * Handles HTTP errors.
   *
   * @param error The HttpErrorResponse object.
   * @returns An Observable that emits an error message.
   */
  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'An unknown error occurred!';
    if (error.error instanceof ErrorEvent) {
      // Client-side errors
      errorMessage = `Error: ${error.error.message}`;
    } else if (error.status === 200 && error.error) {
      // Check if the error is a JSON object with an error property
      errorMessage = error.error.error || 'Server returned an error with a 200 status code.';
    } else {
      // Server-side errors
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    console.error(errorMessage);
    return throwError(() => new Error(errorMessage));
  }
}
