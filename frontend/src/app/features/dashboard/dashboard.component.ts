import { Component, ChangeDetectionStrategy, inject, OnInit, ViewChild, ElementRef, AfterViewInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { RealtimeService } from '../../core/services/realtime.service';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, MatIconModule, RouterModule],
  template: `
    <div class="dashboard-page fade-in">
      <header class="page-header">
        <div class="header-content">
          <h1 class="greeting">Welcome back, {{ authService.currentUser()?.name || 'Gardener' }}! 🌿</h1>
          <p class="subtitle">Your garden is thriving. Here's a real-time overview.</p>
        </div>
        <div class="live-indicator" [class.connected]="realtimeService.isConnected()">
          <span class="dot"></span>
          {{ realtimeService.isConnected() ? 'Live Sync' : 'Connecting...' }}
        </div>
      </header>

      <section class="summary-grid">
        <!-- Watering -->
        <div class="summary-card glass-card" routerLink="/plants">
          <div class="card-icon water-icon">
            <mat-icon>water_drop</mat-icon>
          </div>
          <div class="card-content">
            <span class="card-label">Watering Needed</span>
            <span class="card-value">{{ wateringNeeded() }} Plants</span>
          </div>
          <mat-icon class="arrow-icon">chevron_right</mat-icon>
        </div>

        <!-- Growth -->
        <div class="summary-card glass-card" routerLink="/plants">
          <div class="card-icon growth-icon">
            <mat-icon>trending_up</mat-icon>
          </div>
          <div class="card-content">
            <span class="card-label">Overall Growth</span>
            <span class="card-value">+14%</span>
            <span class="card-subtext">Last 30 days</span>
          </div>
          <mat-icon class="arrow-icon">chevron_right</mat-icon>
        </div>

        <!-- Task -->
        <div class="summary-card glass-card" routerLink="/schedules">
          <div class="card-icon task-icon">
            <mat-icon>event_note</mat-icon>
          </div>
          <div class="card-content">
            <span class="card-label">Upcoming Care</span>
            <span class="card-value">12:45 PM</span>
            <span class="card-subtext">Mist Tropical Ferns</span>
          </div>
          <mat-icon class="arrow-icon">chevron_right</mat-icon>
        </div>
      </section>

      <div class="data-viz-row">
        <!-- Activity Chart -->
        <div class="viz-card glass-card">
          <div class="card-header">
            <h3>Health Vitality Trends</h3>
            <p>Collection average over time</p>
          </div>
          <div class="chart-container">
            <canvas #activityChart></canvas>
          </div>
        </div>

        <!-- Distribution Chart -->
        <div class="viz-card glass-card small">
          <div class="card-header">
            <h3>Care Distribution</h3>
          </div>
          <div class="chart-container">
            <canvas #distChart></canvas>
          </div>
        </div>
      </div>

      <!-- Quick Nav -->
      <section>
        <h2 class="section-title">Quick Access</h2>
        <div class="quick-grid">
          <div class="quick-card glass-card" routerLink="/plants">
            <mat-icon>local_florist</mat-icon>
            <span>My Plants</span>
          </div>
          <div class="quick-card glass-card" routerLink="/schedules">
            <mat-icon>calendar_today</mat-icon>
            <span>Schedules</span>
          </div>
          <div class="quick-card glass-card" routerLink="/knowledge">
            <mat-icon>menu_book</mat-icon>
            <span>Care Guides</span>
          </div>
          <div class="quick-card glass-card" routerLink="/plants">
            <mat-icon>health_and_safety</mat-icon>
            <span>Plant Health</span>
          </div>
        </div>
      </section>
    </div>
  `,
  styles: [`
    .dashboard-page {
      padding: 24px;
      display: flex;
      flex-direction: column;
      gap: 32px;
      min-height: 100%;
    }

    .page-header {
      display: flex;
      justify-content: space-between;
      align-items: flex-start;
    }

    .greeting {
      font-size: 34px;
      margin: 0;
      background: linear-gradient(135deg, #0a1f0a 0%, #2D7A3A 100%);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
    }

    .subtitle {
      color: var(--text-secondary);
      font-weight: 500;
      margin-top: 4px;
    }

    .live-indicator {
      display: flex;
      align-items: center;
      gap: 8px;
      padding: 8px 16px;
      background: rgba(0, 0, 0, 0.05);
      border-radius: 20px;
      font-size: 12px;
      font-weight: 600;
      color: var(--text-muted);
    }

    .live-indicator.connected {
      background: rgba(76, 175, 80, 0.1);
      color: var(--primary-color);
    }

    .live-indicator .dot {
      width: 8px;
      height: 8px;
      border-radius: 50%;
      background: #ccc;
    }

    .live-indicator.connected .dot {
      background: var(--primary-color);
      box-shadow: 0 0 8px var(--primary-color);
      animation: pulse 2s infinite;
    }

    @keyframes pulse {
      0% { transform: scale(1); opacity: 1; }
      50% { transform: scale(1.4); opacity: 0.5; }
      100% { transform: scale(1); opacity: 1; }
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
      border-radius: 28px;
    }

    .card-icon {
      width: 60px;
      height: 60px;
      border-radius: 18px;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 28px;
    }

    .water-icon { background: rgba(30, 136, 229, 0.1); color: #1E88E5; }
    .growth-icon { background: rgba(45, 122, 58, 0.1); color: var(--primary-color); }
    .task-icon { background: rgba(245, 124, 0, 0.1); color: #F57C00; }

    .card-content {
      display: flex;
      flex-direction: column;
      justify-content: center;
      flex: 1;
      overflow: hidden;
    }

    .card-label {
      font-size: 14px;
      font-weight: 600;
      color: var(--text-secondary);
      margin-bottom: 2px;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }

    .card-value {
      font-size: 24px;
      font-weight: 800;
      letter-spacing: -0.5px;
      line-height: 1.2;
      color: var(--text-main);
    }

    .card-subtext {
      font-size: 12px;
      color: var(--text-muted);
      margin-top: 2px;
      font-weight: 500;
    }

    .arrow-icon {
      color: var(--text-muted);
      opacity: 0.5;
      font-size: 20px;
      width: 20px;
      height: 20px;
      transition: var(--transition-fast);
      background: rgba(0, 0, 0, 0.03);
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      padding: 4px;
      box-sizing: content-box;
    }

    .summary-card:hover .arrow-icon {
      opacity: 1;
      transform: translateX(4px);
      color: var(--primary-color);
      background: rgba(45, 122, 58, 0.1);
    }

    .data-viz-row {
      display: grid;
      grid-template-columns: 2fr 1fr;
      gap: 24px;
    }

    .viz-card {
      padding: 28px;
      min-height: 380px;
      display: flex;
      flex-direction: column;
    }

    .chart-container {
      flex: 1;
      position: relative;
      margin-top: 20px;
    }

    .quick-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
      gap: 20px;
    }

    .quick-card {
      padding: 32px 20px;
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 16px;
      font-weight: 700;
      color: var(--text-secondary);
    }

    .quick-card mat-icon {
      font-size: 36px;
      width: 36px;
      height: 36px;
      color: var(--primary-color);
    }

    @media (max-width: 992px) {
      .data-viz-row { grid-template-columns: 1fr; }
    }
  `],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DashboardComponent implements OnInit, AfterViewInit {
  authService = inject(AuthService);
  realtimeService = inject(RealtimeService);

  @ViewChild('activityChart') activityChartCanvas!: ElementRef;
  @ViewChild('distChart') distChartCanvas!: ElementRef;

  wateringNeeded = signal(3);

  ngOnInit() {
    // Listen for real-time care updates to update dashboard stats
    this.realtimeService.careUpdates$.subscribe(() => {
      this.wateringNeeded.update(n => Math.max(0, n - 1));
    });
  }

  ngAfterViewInit() {
    this.initCharts();
  }

  initCharts() {
    // Activity Chart (Line)
    new Chart(this.activityChartCanvas.nativeElement, {
      type: 'line',
      data: {
        labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
        datasets: [{
          label: 'Collection Health',
          data: [82, 84, 83, 86, 88, 87, 89],
          borderColor: '#2D7A3A',
          backgroundColor: 'rgba(45, 122, 58, 0.1)',
          fill: true,
          tension: 0.4
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: { legend: { display: false } },
        scales: {
          y: { beginAtZero: false, min: 70, max: 100 },
          x: { grid: { display: false } }
        }
      }
    });

    // Distribution Chart (Doughnut)
    new Chart(this.distChartCanvas.nativeElement, {
      type: 'doughnut',
      data: {
        labels: ['Watering', 'Feeding', 'Pruning'],
        datasets: [{
          data: [45, 25, 30],
          backgroundColor: ['#1E88E5', '#F57C00', '#2D7A3A'],
          borderWidth: 0
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: { position: 'bottom', labels: { usePointStyle: true, padding: 20 } }
        },
        cutout: '70%'
      }
    });
  }
}

