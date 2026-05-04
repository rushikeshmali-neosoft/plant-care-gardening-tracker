import { Component, ChangeDetectionStrategy, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, MatIconModule],
  template: `
    <div class="dashboard-page fade-in">
      <header class="page-header">
        <h1 class="greeting">Welcome back, {{ authService.currentUser()?.name || 'Gardener' }}!</h1>
        <p class="subtitle">Your garden is thriving today.</p>
      </header>

      <section class="summary-grid">
        <div class="summary-card glass-card">
          <div class="card-icon water-icon">
            <mat-icon>water_drop</mat-icon>
          </div>
          <div class="card-content">
            <span class="card-label">Watering Needed</span>
            <span class="card-value">3 Plants</span>
          </div>
          <mat-icon class="arrow-icon">chevron_right</mat-icon>
        </div>

        <div class="summary-card glass-card">
          <div class="card-icon growth-icon">
            <mat-icon>trending_up</mat-icon>
          </div>
          <div class="card-content">
            <span class="card-label">Growth Status</span>
            <span class="card-value">+12%</span>
          </div>
          <mat-icon class="arrow-icon">chevron_right</mat-icon>
        </div>

        <div class="summary-card glass-card">
          <div class="card-icon task-icon">
            <mat-icon>event_note</mat-icon>
          </div>
          <div class="card-content">
            <span class="card-label">Next Task</span>
            <span class="card-value">2:30 PM</span>
            <span class="card-subtext">Fertilize Monstera</span>
          </div>
          <mat-icon class="arrow-icon">chevron_right</mat-icon>
        </div>
      </section>

      <!-- Placeholder for My Plants Grid -->
      <section class="plants-section">
        <div class="section-header">
          <h2>My Plants</h2>
          <button class="btn-text">View All</button>
        </div>
        <div class="empty-state glass-card">
          <mat-icon>psychology</mat-icon>
          <p>You haven't added any plants yet.</p>
          <button class="btn-primary-sm">Add Your First Plant</button>
        </div>
      </section>
    </div>
  `,
  styles: [`
    .dashboard-page {
      padding: 40px;
      display: flex;
      flex-direction: column;
      gap: 40px;
    }

    .greeting {
      font-family: 'Outfit', sans-serif;
      font-size: 32px;
      margin: 0;
      color: white;
    }

    .subtitle {
      color: rgba(255, 255, 255, 0.5);
      margin: 4px 0 0 0;
    }

    .summary-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
      gap: 24px;
    }

    .summary-card {
      padding: 24px;
      display: flex;
      align-items: center;
      gap: 20px;
      cursor: pointer;
      transition: transform 0.2s, background 0.2s;
    }

    .summary-card:hover {
      transform: translateY(-4px);
      background: rgba(255, 255, 255, 0.1);
    }

    .card-icon {
      width: 56px;
      height: 56px;
      border-radius: 16px;
      display: flex;
      align-items: center;
      justify-content: center;
    }

    .card-icon mat-icon {
      font-size: 28px;
      width: 28px;
      height: 28px;
    }

    .water-icon { background: rgba(33, 150, 243, 0.15); color: #64b5f6; }
    .growth-icon { background: rgba(76, 175, 80, 0.15); color: #81c784; }
    .task-icon { background: rgba(255, 152, 0, 0.15); color: #ffb74d; }

    .card-content {
      flex: 1;
      display: flex;
      flex-direction: column;
    }

    .card-label {
      font-size: 14px;
      color: rgba(255, 255, 255, 0.5);
    }

    .card-value {
      font-size: 20px;
      font-weight: 600;
      color: white;
    }

    .card-subtext {
      font-size: 12px;
      color: var(--primary-light);
    }

    .arrow-icon {
      color: rgba(255, 255, 255, 0.2);
    }

    .section-header {
      display: flex;
      align-items: center;
      justify-content: space-between;
      margin-bottom: 24px;
    }

    .section-header h2 {
      font-family: 'Outfit', sans-serif;
      font-size: 24px;
      margin: 0;
    }

    .empty-state {
      padding: 60px;
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 16px;
      color: rgba(255, 255, 255, 0.4);
    }

    .empty-state mat-icon {
      font-size: 48px;
      width: 48px;
      height: 48px;
    }

    .btn-text {
      background: none;
      border: none;
      color: var(--primary-light);
      font-weight: 600;
      cursor: pointer;
    }

    .btn-primary-sm {
      background: var(--primary-color);
      color: white;
      border: none;
      padding: 10px 20px;
      border-radius: 10px;
      font-weight: 600;
      cursor: pointer;
    }

    /* Responsive Queries */
    @media (max-width: 768px) {
      .dashboard-page {
        padding: 24px;
        gap: 32px;
      }

      .greeting {
        font-size: 26px;
      }

      .summary-grid {
        grid-template-columns: 1fr;
      }

      .empty-state {
        padding: 40px 24px;
        text-align: center;
      }
    }

    @media (max-width: 480px) {
      .greeting {
        font-size: 22px;
      }

      .summary-card {
        padding: 16px;
        gap: 16px;
      }

      .card-icon {
        width: 48px;
        height: 48px;
      }
    }
  `],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DashboardComponent {
  authService = inject(AuthService);
}
