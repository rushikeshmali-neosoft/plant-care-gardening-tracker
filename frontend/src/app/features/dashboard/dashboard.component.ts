import { Component, ChangeDetectionStrategy, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, MatIconModule, RouterModule],
  template: `
    <div class="dashboard-page fade-in">
      <header class="page-header">
        <div>
          <h1 class="greeting">Welcome back, {{ authService.currentUser()?.name || 'Gardener' }}! 🌿</h1>
          <p class="subtitle">Your garden dashboard — everything at a glance.</p>
        </div>
      </header>

      <section class="summary-grid">
        <!-- Watering -->
        <div class="summary-card" routerLink="/plants">
          <div class="card-icon water-icon">
            <mat-icon>water_drop</mat-icon>
          </div>
          <div class="card-content">
            <span class="card-label">Watering Needed</span>
            <span class="card-value">3 Plants</span>
          </div>
          <mat-icon class="arrow-icon">chevron_right</mat-icon>
        </div>

        <!-- Growth -->
        <div class="summary-card" routerLink="/plants">
          <div class="card-icon growth-icon">
            <mat-icon>trending_up</mat-icon>
          </div>
          <div class="card-content">
            <span class="card-label">Growth Status</span>
            <span class="card-value">+12%</span>
          </div>
          <mat-icon class="arrow-icon">chevron_right</mat-icon>
        </div>

        <!-- Task -->
        <div class="summary-card" routerLink="/schedules">
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

      <!-- Quick Nav Cards -->
      <section>
        <h2 class="section-title">Quick Access</h2>
        <div class="quick-grid">
          <div class="quick-card" routerLink="/plants">
            <mat-icon>local_florist</mat-icon>
            <span>My Plants</span>
          </div>
          <div class="quick-card" routerLink="/schedules">
            <mat-icon>calendar_today</mat-icon>
            <span>Schedules</span>
          </div>
          <div class="quick-card" routerLink="/knowledge">
            <mat-icon>menu_book</mat-icon>
            <span>Care Guides</span>
          </div>
          <div class="quick-card" routerLink="/plants">
            <mat-icon>health_and_safety</mat-icon>
            <span>Plant Health</span>
          </div>
        </div>
      </section>

      <!-- My Plants section -->
      <section class="plants-section">
        <div class="section-header">
          <h2 class="section-title" style="margin:0">My Plants</h2>
          <a routerLink="/plants" class="view-all-link">View All →</a>
        </div>
        <div class="empty-state-card">
          <mat-icon>local_florist</mat-icon>
          <h3>Start Your Garden</h3>
          <p>You haven't added any plants yet. Add your first plant to get started!</p>
          <button class="btn-add" routerLink="/plants">Add Your First Plant</button>
        </div>
      </section>
    </div>
  `,
  styles: [`
    .dashboard-page {
      padding: 36px 40px;
      display: flex;
      flex-direction: column;
      gap: 36px;
      background: var(--bg-color);
      min-height: 100%;
    }

    /* ─── Header ─── */
    .greeting {
      font-family: 'Outfit', sans-serif;
      font-size: 30px;
      font-weight: 700;
      margin: 0;
      color: var(--text-main);
    }

    .subtitle {
      color: var(--text-muted);
      margin: 6px 0 0 0;
      font-size: 15px;
    }

    /* ─── Summary Cards ─── */
    .summary-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
      gap: 20px;
    }

    .summary-card {
      background: #FFFFFF;
      border: 1px solid var(--border-color);
      border-radius: 18px;
      padding: 22px 24px;
      display: flex;
      align-items: center;
      gap: 18px;
      cursor: pointer;
      transition: transform 0.18s, box-shadow 0.18s, border-color 0.18s;
      box-shadow: var(--shadow-sm);
      text-decoration: none;
    }

    .summary-card:hover {
      transform: translateY(-3px);
      box-shadow: var(--shadow-md);
      border-color: var(--primary-100);
    }

    .card-icon {
      width: 54px;
      height: 54px;
      border-radius: 14px;
      display: flex;
      align-items: center;
      justify-content: center;
      flex-shrink: 0;
    }

    .card-icon mat-icon {
      font-size: 26px;
      width: 26px;
      height: 26px;
    }

    .water-icon  { background: #E3F2FD; color: #1E88E5; }
    .growth-icon { background: var(--primary-50); color: var(--primary-color); }
    .task-icon   { background: #FFF3E0; color: #F57C00; }

    .card-content {
      flex: 1;
      display: flex;
      flex-direction: column;
      gap: 2px;
    }

    .card-label {
      font-size: 13px;
      color: var(--text-muted);
      font-weight: 500;
    }

    .card-value {
      font-size: 22px;
      font-weight: 700;
      color: var(--text-main);
      line-height: 1.2;
    }

    .card-subtext {
      font-size: 12px;
      color: var(--primary-color);
      font-weight: 500;
    }

    .arrow-icon {
      color: var(--text-muted);
      opacity: 0.5;
    }

    /* ─── Section Title ─── */
    .section-title {
      font-family: 'Outfit', sans-serif;
      font-size: 20px;
      font-weight: 600;
      color: var(--text-main);
      margin: 0 0 16px 0;
    }

    /* ─── Quick Access Grid ─── */
    .quick-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(140px, 1fr));
      gap: 14px;
    }

    .quick-card {
      background: #FFFFFF;
      border: 1px solid var(--border-color);
      border-radius: 16px;
      padding: 22px 16px;
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 10px;
      cursor: pointer;
      transition: transform 0.18s, box-shadow 0.18s, border-color 0.18s, background 0.18s;
      box-shadow: var(--shadow-sm);
      font-size: 13px;
      font-weight: 600;
      color: var(--text-secondary);
      text-decoration: none;
    }

    .quick-card mat-icon {
      font-size: 28px;
      width: 28px;
      height: 28px;
      color: var(--primary-color);
    }

    .quick-card:hover {
      transform: translateY(-3px);
      box-shadow: var(--shadow-md);
      border-color: var(--primary-100);
      background: var(--primary-50);
      color: var(--primary-dark);
    }

    /* ─── Section Header ─── */
    .section-header {
      display: flex;
      align-items: center;
      justify-content: space-between;
      margin-bottom: 16px;
    }

    .view-all-link {
      color: var(--primary-color);
      font-size: 14px;
      font-weight: 600;
      text-decoration: none;
      transition: color 0.18s;
    }

    .view-all-link:hover { color: var(--primary-dark); }

    /* ─── Empty State ─── */
    .empty-state-card {
      background: #FFFFFF;
      border: 2px dashed var(--primary-100);
      border-radius: 18px;
      padding: 60px 40px;
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 14px;
      text-align: center;
    }

    .empty-state-card mat-icon {
      font-size: 52px;
      width: 52px;
      height: 52px;
      color: var(--primary-100);
    }

    .empty-state-card h3 {
      font-size: 20px;
      color: var(--text-main);
      margin: 0;
    }

    .empty-state-card p {
      color: var(--text-muted);
      max-width: 360px;
      margin: 0;
    }

    .btn-add {
      background: var(--primary-color);
      color: #FFFFFF;
      border: none;
      padding: 12px 28px;
      border-radius: 12px;
      font-weight: 600;
      font-size: 14px;
      cursor: pointer;
      transition: background 0.18s, transform 0.18s;
      margin-top: 6px;
    }

    .btn-add:hover {
      background: var(--primary-dark);
      transform: translateY(-1px);
    }

    /* ─── Responsive ─── */
    @media (max-width: 768px) {
      .dashboard-page { padding: 24px 20px; gap: 28px; }
      .greeting { font-size: 24px; }
      .summary-grid { grid-template-columns: 1fr; }
      .quick-grid { grid-template-columns: repeat(2, 1fr); }
    }
  `],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DashboardComponent {
  authService = inject(AuthService);
}
