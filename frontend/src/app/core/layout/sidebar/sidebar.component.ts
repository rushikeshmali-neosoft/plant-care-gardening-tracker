import { Component, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTooltipModule } from '@angular/material/tooltip';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule, MatIconModule, MatButtonModule, MatTooltipModule],
  template: `
    <aside class="sidebar glass-sidebar">
      <div class="logo-container">
        <mat-icon class="logo-icon">eco</mat-icon>
        <span class="logo-text">FloraCare</span>
      </div>

      <nav class="nav-menu">
        <a *ngFor="let item of navItems" 
           [routerLink]="item.path" 
           routerLinkActive="active" 
           class="nav-item"
           [matTooltip]="item.label"
           matTooltipPosition="right">
          <mat-icon>{{ item.icon }}</mat-icon>
          <span class="nav-label">{{ item.label }}</span>
        </a>
      </nav>

      <div class="sidebar-footer">
        <button mat-icon-button class="settings-btn">
          <mat-icon>settings</mat-icon>
        </button>
      </div>
    </aside>
  `,
  styles: [`
    .sidebar {
      width: 260px;
      height: 100vh;
      display: flex;
      flex-direction: column;
      padding: 24px;
      border-right: 1px solid rgba(255, 255, 255, 0.1);
      transition: all 0.3s ease;
    }

    .glass-sidebar {
      background: rgba(255, 255, 255, 0.05);
      backdrop-filter: blur(20px);
    }

    .logo-container {
      display: flex;
      align-items: center;
      gap: 12px;
      padding: 0 12px;
      margin-bottom: 48px;
    }

    .logo-icon {
      color: var(--primary-light);
      font-size: 32px;
      width: 32px;
      height: 32px;
    }

    .logo-text {
      font-family: 'Outfit', sans-serif;
      font-size: 22px;
      font-weight: 600;
      color: white;
      letter-spacing: 0.5px;
    }

    .nav-menu {
      display: flex;
      flex-direction: column;
      gap: 8px;
      flex: 1;
    }

    .nav-item {
      display: flex;
      align-items: center;
      gap: 16px;
      padding: 12px 16px;
      border-radius: 12px;
      color: rgba(255, 255, 255, 0.6);
      text-decoration: none;
      transition: all 0.2s ease;
    }

    .nav-item:hover {
      background: rgba(255, 255, 255, 0.05);
      color: white;
    }

    .nav-item.active {
      background: rgba(255, 255, 255, 0.1);
      color: var(--primary-light);
      box-shadow: inset 0 0 0 1px rgba(255, 255, 255, 0.1);
    }

    .nav-label {
      font-size: 15px;
      font-weight: 500;
    }

    .sidebar-footer {
      padding-top: 24px;
      border-top: 1px solid rgba(255, 255, 255, 0.1);
      display: flex;
      justify-content: center;
    }

    .settings-btn {
      color: rgba(255, 255, 255, 0.5);
    }

    @media (max-width: 992px) {
      .sidebar {
        width: 80px;
        padding: 24px 12px;
      }
      .logo-text, .nav-label {
        display: none;
      }
      .logo-container {
        justify-content: center;
        padding: 0;
      }
    }
  `],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SidebarComponent {
  navItems = [
    { label: 'Dashboard', icon: 'grid_view', path: '/dashboard' },
    { label: 'My Plants', icon: 'local_florist', path: '/plants' },
    { label: 'Schedules', icon: 'calendar_today', path: '/schedules' },
    { label: 'Health', icon: 'health_and_safety', path: '/health' },
    { label: 'Knowledge', icon: 'menu_book', path: '/knowledge' }
  ];
}
