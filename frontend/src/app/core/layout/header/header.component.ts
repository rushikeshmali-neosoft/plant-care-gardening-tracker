import { Component, ChangeDetectionStrategy, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import { AuthService } from '../../services/auth.service';
import { NotificationCenterComponent } from '../../../features/notifications/notification-center/notification-center.component';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, MatIconModule, MatButtonModule, MatMenuModule, NotificationCenterComponent],
  template: `
    <header class="header glass-header">
      <div class="search-container">
        <mat-icon class="search-icon">search</mat-icon>
        <input type="text" placeholder="Search plants, guides..." class="search-input">
      </div>

      <div class="actions-container">
        <app-notification-center></app-notification-center>

        <div class="profile-section" [matMenuTriggerFor]="profileMenu">
          <div class="user-info">
            <span class="user-name">{{ authService.currentUser()?.name || 'Gardener' }}</span>
            <span class="user-role">{{ authService.currentUser()?.experienceLevel || 'Beginner' }}</span>
          </div>
          <div class="avatar">
            {{ (authService.currentUser()?.name || 'G').charAt(0) }}
          </div>
        </div>

        <mat-menu #profileMenu="matMenu" class="profile-dropdown">
          <button mat-menu-item>
            <mat-icon>person_outline</mat-icon>
            <span>My Profile</span>
          </button>
          <button mat-menu-item (click)="authService.logout()">
            <mat-icon>logout</mat-icon>
            <span>Logout</span>
          </button>
        </mat-menu>
      </div>
    </header>
  `,
  styles: [`
    .header {
      height: 80px;
      padding: 0 40px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      border-bottom: 1px solid rgba(255, 255, 255, 0.05);
    }

    .glass-header {
      background: rgba(255, 255, 255, 0.02);
      backdrop-filter: blur(10px);
    }

    .search-container {
      display: flex;
      align-items: center;
      gap: 12px;
      background: rgba(255, 255, 255, 0.05);
      padding: 10px 20px;
      border-radius: 14px;
      width: 400px;
      border: 1px solid rgba(255, 255, 255, 0.05);
    }

    .search-icon {
      color: rgba(255, 255, 255, 0.4);
      font-size: 20px;
      width: 20px;
      height: 20px;
    }

    .search-input {
      background: none;
      border: none;
      color: white;
      width: 100%;
      outline: none;
      font-size: 14px;
    }

    .actions-container {
      display: flex;
      align-items: center;
      gap: 24px;
    }

    .action-btn {
      color: rgba(255, 255, 255, 0.7);
      position: relative;
    }

    .notification-badge {
      position: absolute;
      top: 10px;
      right: 10px;
      width: 8px;
      height: 8px;
      background: var(--primary-light);
      border-radius: 50%;
      border: 2px solid #1a1a1a;
    }

    .profile-section {
      display: flex;
      align-items: center;
      gap: 16px;
      cursor: pointer;
      padding: 6px 6px 6px 16px;
      border-radius: 16px;
      transition: background 0.2s;
    }

    .profile-section:hover {
      background: rgba(255, 255, 255, 0.05);
    }

    .user-info {
      display: flex;
      flex-direction: column;
      align-items: flex-end;
    }

    .user-name {
      color: white;
      font-size: 14px;
      font-weight: 600;
    }

    .user-role {
      color: rgba(255, 255, 255, 0.5);
      font-size: 12px;
      text-transform: capitalize;
    }

    .avatar {
      width: 40px;
      height: 40px;
      border-radius: 12px;
      background: linear-gradient(135deg, var(--primary-color), var(--primary-light));
      display: flex;
      align-items: center;
      justify-content: center;
      color: white;
      font-weight: 700;
      font-size: 18px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.2);
    }

    @media (max-width: 768px) {
      .search-container {
        display: none;
      }
      .user-info {
        display: none;
      }
    }
  `],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HeaderComponent {
  authService = inject(AuthService);
}
