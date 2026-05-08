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
      width: 260px;
      height: calc(100vh - 32px);
      margin: 16px;
      display: flex;
      flex-direction: column;
      padding: 32px 20px;
      background: var(--glass-bg);
      backdrop-filter: var(--glass-blur);
      -webkit-backdrop-filter: var(--glass-blur);
      border: 1px solid var(--glass-border);
      border-radius: 28px;
      box-shadow: var(--glass-shadow);
      transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
      z-index: 100;
    }

    .logo-container {
      display: flex;
      align-items: center;
      gap: 14px;
      padding: 0 10px;
      margin-bottom: 48px;
    }

    .logo-icon-wrap {
      width: 42px;
      height: 42px;
      border-radius: 14px;
      background: linear-gradient(135deg, var(--primary-color), var(--primary-light));
      display: flex;
      align-items: center;
      justify-content: center;
      box-shadow: var(--shadow-green);
      flex-shrink: 0;
    }

    .logo-icon {
      color: #FFFFFF;
      font-size: 24px;
      width: 24px;
      height: 24px;
    }

    .logo-text {
      font-family: 'Outfit', sans-serif;
      font-size: 22px;
      font-weight: 800;
      color: var(--text-main);
      letter-spacing: -0.5px;
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
      padding: 14px 18px;
      border-radius: 16px;
      color: var(--text-secondary);
      text-decoration: none;
      font-size: 15px;
      font-weight: 500;
      transition: all 0.25s cubic-bezier(0.4, 0, 0.2, 1);
    }

    .nav-item mat-icon {
      font-size: 22px;
      width: 22px;
      height: 22px;
      flex-shrink: 0;
      opacity: 0.8;
    }

    .nav-item:hover {
      background: rgba(255, 255, 255, 0.5);
      color: var(--primary-color);
      transform: translateX(4px);
    }

    .nav-item.active {
      background: var(--primary-color);
      color: white;
      font-weight: 600;
      box-shadow: 0 8px 16px rgba(45, 122, 58, 0.3);
    }

    .nav-item.active mat-icon {
      opacity: 1;
    }

    .sidebar-footer {
      padding-top: 24px;
      border-top: 1px solid var(--glass-border);
      display: flex;
      justify-content: center;
    }

    .settings-btn {
      color: var(--text-muted);
      transition: all 0.3s;
    }

    .settings-btn:hover {
      color: var(--primary-color);
      transform: rotate(45deg);
    }

    @media (max-width: 1200px) {
      .sidebar {
        width: 88px;
        padding: 32px 14px;
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
        padding: 14px;
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
