import { Component, ChangeDetectionStrategy, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { AuthService } from '../../services/auth.service';
import { NotificationCenterComponent } from '../../../features/notifications/notification-center/notification-center.component';
import { ProfileModalComponent } from '../../../features/profile/profile-modal.component';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, MatIconModule, MatButtonModule, MatMenuModule, MatDialogModule, NotificationCenterComponent],
  template: `
    <header class="header">
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
          <button mat-menu-item (click)="openProfile()">
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
      height: 72px;
      padding: 0 32px;
      margin: 16px 16px 0 0;
      display: flex;
      align-items: center;
      justify-content: space-between;
      background: var(--glass-bg);
      backdrop-filter: var(--glass-blur);
      -webkit-backdrop-filter: var(--glass-blur);
      border: 1px solid var(--glass-border);
      border-radius: 20px;
      box-shadow: var(--glass-shadow);
      position: sticky;
      top: 16px;
      z-index: 99;
    }

    .search-container {
      display: flex;
      align-items: center;
      gap: 12px;
      background: rgba(255, 255, 255, 0.2);
      padding: 10px 20px;
      border-radius: 14px;
      width: 400px;
      border: 1px solid rgba(255, 255, 255, 0.1);
      transition: all 0.3s ease;
    }

    .search-container:focus-within {
      background: rgba(255, 255, 255, 0.4);
      border-color: var(--primary-light);
      box-shadow: 0 0 0 4px rgba(76, 175, 80, 0.1);
      width: 440px;
    }

    .search-icon {
      color: var(--text-secondary);
      font-size: 20px;
      width: 20px;
      height: 20px;
    }

    .search-input {
      background: none;
      border: none;
      color: var(--text-main);
      width: 100%;
      outline: none;
      font-size: 15px;
      font-family: 'Inter', sans-serif;
      font-weight: 500;
    }

    .search-input::placeholder {
      color: var(--text-muted);
    }

    .actions-container {
      display: flex;
      align-items: center;
      gap: 20px;
    }

    .profile-section {
      display: flex;
      align-items: center;
      gap: 14px;
      cursor: pointer;
      padding: 6px 6px 6px 16px;
      border-radius: 16px;
      background: rgba(255, 255, 255, 0.2);
      border: 1px solid rgba(255, 255, 255, 0.1);
      transition: all 0.3s ease;
    }

    .profile-section:hover {
      background: rgba(255, 255, 255, 0.4);
      box-shadow: var(--shadow-sm);
    }

    .user-info {
      display: flex;
      flex-direction: column;
      align-items: flex-end;
    }

    .user-name {
      color: var(--text-main);
      font-size: 14px;
      font-weight: 700;
      line-height: 1.2;
    }

    .user-role {
      color: var(--text-secondary);
      font-size: 11px;
      font-weight: 600;
      text-transform: uppercase;
      letter-spacing: 0.5px;
      line-height: 1.2;
      opacity: 0.8;
    }

    .avatar {
      width: 40px;
      height: 40px;
      border-radius: 12px;
      background: linear-gradient(135deg, var(--primary-color), var(--primary-light));
      display: flex;
      align-items: center;
      justify-content: center;
      color: #FFFFFF;
      font-weight: 800;
      font-size: 18px;
      box-shadow: var(--shadow-green);
      flex-shrink: 0;
    }

    @media (max-width: 768px) {
      .search-container { display: none; }
      .user-info { display: none; }
      .header { padding: 0 16px; margin: 12px 12px 0 12px; }
    }
  `],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HeaderComponent {
  authService = inject(AuthService);
  private dialog = inject(MatDialog);

  openProfile(): void {
    this.dialog.open(ProfileModalComponent, {
      width: '450px',
      panelClass: 'profile-modal-panel'
    });
  }
}
