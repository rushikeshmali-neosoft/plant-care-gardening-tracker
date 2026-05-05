import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login.component';
import { RegisterComponent } from './features/auth/register/register.component';
import { DashboardComponent } from './features/dashboard/dashboard.component';
import { PlantListComponent } from './features/plants/plant-list/plant-list.component';
import { PlantDetailComponent } from './features/plants/plant-detail/plant-detail.component';
import { GrowthHistoryComponent } from './features/plants/growth-history/growth-history.component';
import { CareHistoryComponent } from './features/plants/care-history/care-history.component';
import { HealthDashboardComponent } from './features/plants/health-dashboard/health-dashboard.component';
import { CareGuideListComponent } from './features/knowledge/guide-list/guide-list.component';
import { CareGuideDetailComponent } from './features/knowledge/guide-detail/guide-detail.component';
import { CareCalendarComponent } from './features/schedules/care-calendar/care-calendar.component';
import { MainLayoutComponent } from './core/layout/main-layout/main-layout.component';
import { authGuard } from './core/guards/auth.guard';
import { HealthListComponent } from './features/health/health-list/health-list.component';

import { ScheduleListComponent } from './features/schedules/schedule-list/schedule-list.component';

export const routes: Routes = [
  { path: 'auth/login', component: LoginComponent },
  { path: 'auth/register', component: RegisterComponent },
  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: 'dashboard', component: DashboardComponent },
      { path: 'plants', component: PlantListComponent },
      { path: 'plants/:id', component: PlantDetailComponent },
      { path: 'plants/:id/growth', component: GrowthHistoryComponent },
      { path: 'plants/:id/history', component: CareHistoryComponent },
      { path: 'plants/:id/health', component: HealthDashboardComponent },
      { path: 'health', component: HealthListComponent },
      { path: 'knowledge', component: CareGuideListComponent },
      { path: 'knowledge/:id', component: CareGuideDetailComponent },
      {
        path: 'schedules',
        children: [
          { path: 'calendar', component: CareCalendarComponent },
          { path: 'list', component: ScheduleListComponent },
          { path: '', redirectTo: 'calendar', pathMatch: 'full' }
        ]
      },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  },
  { path: '**', redirectTo: 'dashboard' }
];
