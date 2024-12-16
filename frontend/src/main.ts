import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { provideRouter } from '@angular/router'; // Import provideRouter
import { routes } from './app/app.routes'; // Import your routes
import { importProvidersFrom } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

/**
 * Bootstrap the application.
 */
bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes), // Provide the routes
    importProvidersFrom(HttpClientModule) // Provide HttpClient
  ]
}).catch(err => console.error(err));
