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
    <aside class="sidebar">
      <div class="logo-container">
        <div class="logo-icon-wrap">
          <mat-icon class="logo-icon">eco</mat-icon>
        </div>
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
        <button mat-icon-button class="settings-btn" matTooltip="Settings" matTooltipPosition="right">
          <mat-icon>settings</mat-icon>
        </button>
      </div>
    </aside>
  `,
  styles: [`
    .sidebar {
      width: 240px;
      height: 100vh;
      display: flex;
      flex-direction: column;
      padding: 24px 16px;
      background: #FFFFFF;
      border-right: 1px solid var(--border-color);
      box-shadow: 2px 0 12px rgba(0, 0, 0, 0.04);
      transition: width 0.3s ease;
    }

    .logo-container {
      display: flex;
      align-items: center;
      gap: 12px;
      padding: 0 8px;
      margin-bottom: 40px;
    }

    .logo-icon-wrap {
      width: 38px;
      height: 38px;
      border-radius: 10px;
      background: linear-gradient(135deg, var(--primary-color), var(--primary-light));
      display: flex;
      align-items: center;
      justify-content: center;
      box-shadow: var(--shadow-green);
      flex-shrink: 0;
    }

    .logo-icon {
      color: #FFFFFF;
      font-size: 22px;
      width: 22px;
      height: 22px;
    }

    .logo-text {
      font-family: 'Outfit', sans-serif;
      font-size: 20px;
      font-weight: 700;
      color: var(--primary-dark);
      letter-spacing: 0.3px;
    }

    .nav-menu {
      display: flex;
      flex-direction: column;
      gap: 4px;
      flex: 1;
    }

    .nav-item {
      display: flex;
      align-items: center;
      gap: 14px;
      padding: 11px 14px;
      border-radius: 12px;
      color: var(--text-muted);
      text-decoration: none;
      font-size: 14px;
      font-weight: 500;
      transition: all 0.18s ease;
    }

    .nav-item mat-icon {
      font-size: 20px;
      width: 20px;
      height: 20px;
      flex-shrink: 0;
    }

    .nav-item:hover {
      background: var(--primary-50);
      color: var(--primary-color);
    }

    .nav-item.active {
      background: var(--primary-50);
      color: var(--primary-color);
      font-weight: 600;
      box-shadow: inset 3px 0 0 var(--primary-color);
    }

    .sidebar-footer {
      padding-top: 20px;
      border-top: 1px solid var(--border-color);
      display: flex;
      justify-content: center;
    }

    .settings-btn {
      color: var(--text-muted);
      transition: color 0.18s;
    }

    .settings-btn:hover {
      color: var(--primary-color);
    }

    @media (max-width: 992px) {
      .sidebar {
        width: 72px;
        padding: 24px 10px;
      }
      .logo-text, .nav-label {
        display: none;
      }
      .logo-container {
        justify-content: center;
        padding: 0;
      }
      .nav-item {
        justify-content: center;
        padding: 11px;
      }
    }
  `],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SidebarComponent {
  navItems = [
    { label: 'Dashboard',  icon: 'grid_view',         path: '/dashboard' },
    { label: 'My Plants',  icon: 'local_florist',     path: '/plants' },
    { label: 'Schedules',  icon: 'calendar_today',    path: '/schedules' },
    { label: 'Health',     icon: 'health_and_safety', path: '/health' },
    { label: 'Knowledge',  icon: 'menu_book',         path: '/knowledge' }
  ];
}
