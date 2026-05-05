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
      display: flex;
      align-items: center;
      justify-content: space-between;
      background: #FFFFFF;
      border-bottom: 1px solid var(--border-color);
      box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05);
      position: sticky;
      top: 0;
      z-index: 100;
    }

    .search-container {
      display: flex;
      align-items: center;
      gap: 10px;
      background: var(--bg-color);
      padding: 9px 18px;
      border-radius: 12px;
      width: 380px;
      border: 1px solid var(--border-color);
      transition: border-color 0.18s, box-shadow 0.18s;
    }

    .search-container:focus-within {
      border-color: var(--primary-light);
      box-shadow: 0 0 0 3px rgba(76, 175, 80, 0.12);
    }

    .search-icon {
      color: var(--text-muted);
      font-size: 18px;
      width: 18px;
      height: 18px;
    }

    .search-input {
      background: none;
      border: none;
      color: var(--text-main);
      width: 100%;
      outline: none;
      font-size: 14px;
      font-family: 'Inter', sans-serif;
    }

    .search-input::placeholder {
      color: var(--text-muted);
    }

    .actions-container {
      display: flex;
      align-items: center;
      gap: 16px;
    }

    .profile-section {
      display: flex;
      align-items: center;
      gap: 12px;
      cursor: pointer;
      padding: 6px 8px 6px 14px;
      border-radius: 14px;
      border: 1px solid var(--border-color);
      background: #FFFFFF;
      transition: background 0.18s, box-shadow 0.18s;
    }

    .profile-section:hover {
      background: var(--bg-color);
      box-shadow: var(--shadow-sm);
    }

    .user-info {
      display: flex;
      flex-direction: column;
      align-items: flex-end;
    }

    .user-name {
      color: var(--text-main);
      font-size: 13px;
      font-weight: 600;
      line-height: 1.2;
    }

    .user-role {
      color: var(--text-muted);
      font-size: 11px;
      text-transform: capitalize;
      line-height: 1.2;
    }

    .avatar {
      width: 36px;
      height: 36px;
      border-radius: 10px;
      background: linear-gradient(135deg, var(--primary-color), var(--primary-light));
      display: flex;
      align-items: center;
      justify-content: center;
      color: #FFFFFF;
      font-weight: 700;
      font-size: 16px;
      box-shadow: var(--shadow-green);
      flex-shrink: 0;
    }

    @media (max-width: 768px) {
      .search-container { display: none; }
      .user-info { display: none; }
      .header { padding: 0 16px; }
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
