import { Component, OnInit, ChangeDetectionStrategy, inject, signal, computed, ViewChild, ElementRef, AfterViewInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { HealthService } from '../../../core/services/health.service';
import { GrowthService } from '../../../core/services/growth.service';
import { RealtimeService } from '../../../core/services/realtime.service';
import { HealthStatus, HealthIndicator } from '../../../core/models/health.model';
import { Chart, registerables } from 'chart.js';
import { Subscription } from 'rxjs';

Chart.register(...registerables);

@Component({
  selector: 'app-health-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatIconModule,
    MatButtonModule,
    MatProgressBarModule
  ],
  templateUrl: './health-dashboard.component.html',
  styleUrls: ['./health-dashboard.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HealthDashboardComponent implements OnInit, AfterViewInit, OnDestroy {
  private route = inject(ActivatedRoute);
  public healthService = inject(HealthService);
  public growthService = inject(GrowthService);
  private realtimeService = inject(RealtimeService);

  @ViewChild('healthChart') healthChartCanvas!: ElementRef;
  
  plantId = signal<number | null>(null);
  chart: any;
  private subscriptions: Subscription[] = [];

  // Exposed for template
  analysis = this.healthService.healthAnalysis;
  growth = this.growthService.measurements;

  currentHealthScore = computed(() => {
    const analysis = this.analysis();
    if (!analysis || analysis.indicators.length === 0) return 85;
    return analysis.indicators[0].healthScore;
  });

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.plantId.set(id);
      this.healthService.getFullAnalysis(id).subscribe();
      this.growthService.getMeasurementsByPlant(id).subscribe();
      
      // Initiate real-time subscription
      this.realtimeService.subscribeToHealth(id);
      // Real-time subscription
      const sub = this.realtimeService.healthUpdates$.subscribe(update => {
        if (update.plantId === id) {
          this.healthService.getFullAnalysis(id).subscribe(() => {
            this.updateChartData();
          });
        }
      });
      this.subscriptions.push(sub);
    }
  }

  ngAfterViewInit() {
    this.initChart();
  }

  initChart() {
    const ctx = this.healthChartCanvas.nativeElement.getContext('2d');
    const gradient = ctx.createLinearGradient(0, 0, 0, 400);
    gradient.addColorStop(0, 'rgba(45, 122, 58, 0.4)');
    gradient.addColorStop(1, 'rgba(45, 122, 58, 0)');

    this.chart = new Chart(ctx, {
      type: 'line',
      data: {
        labels: [],
        datasets: [{
          label: 'Health Vitality',
          data: [],
          borderColor: '#2D7A3A',
          backgroundColor: gradient,
          fill: true,
          tension: 0.4,
          pointRadius: 4,
          pointBackgroundColor: '#FFFFFF',
          pointBorderColor: '#2D7A3A',
          pointBorderWidth: 2
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: { display: false },
          tooltip: {
            backgroundColor: 'rgba(255, 255, 255, 0.9)',
            titleColor: '#0a1f0a',
            bodyColor: '#2c4a2c',
            borderColor: 'rgba(45, 122, 58, 0.2)',
            borderWidth: 1,
            padding: 12,
            cornerRadius: 12,
            displayColors: false
          }
        },
        scales: {
          y: {
            beginAtZero: true,
            max: 100,
            grid: { color: 'rgba(0,0,0,0.05)' },
            ticks: { font: { family: 'Inter' } }
          },
          x: {
            grid: { display: false },
            ticks: { font: { family: 'Inter' } }
          }
        }
      }
    });

    this.updateChartData();
  }

  updateChartData() {
    const analysis = this.analysis();
    if (!analysis || !this.chart) return;

    const records = [...analysis.indicators].reverse();
    this.chart.data.labels = records.map(r => new Date(r.recordedDate).toLocaleDateString());
    this.chart.data.datasets[0].data = records.map(r => r.healthScore);
    this.chart.update('none'); // Update without animation for real-time feel if frequent
  }

  ngOnDestroy() {
    this.subscriptions.forEach(s => s.unsubscribe());
    if (this.chart) this.chart.destroy();
  }

  getStatusLabel(status: HealthStatus): string {
    return status.replace(/_/g, ' ').toLowerCase();
  }
}
