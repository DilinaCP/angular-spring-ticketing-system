import { Routes } from '@angular/router';
import { ConfigurationFormComponent } from './configuration-form/configuration-form.component';
import { ControlPanelComponent } from './control-panel/control-panel.component';
import { LogDisplayComponent } from './log-display/log-display.component';
import { TicketDisplayComponent } from './ticket-display/ticket-display.component';

/**
 * Application routes.
 */
export const routes: Routes = [
    { path: 'configuration', component: ConfigurationFormComponent },
    { path: 'control', component: ControlPanelComponent },
    { path: 'logs', component: LogDisplayComponent },
    { path: 'tickets', component: TicketDisplayComponent },
    { path: '', redirectTo: '/configuration', pathMatch: 'full' }, // Default route
];
