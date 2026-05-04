import { Component, OnInit, ChangeDetectionStrategy, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { HealthService } from '../../../core/services/health.service';
import { GrowthService } from '../../../core/services/growth.service';
import { HealthStatus } from '../../../core/models/health.model';

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
export class HealthDashboardComponent implements OnInit {
  private route = inject(ActivatedRoute);
  healthService = inject(HealthService);
  growthService = inject(GrowthService);

  plantId = signal<number | null>(null);

  // Health Score calculation (0-100)
  currentHealthScore = computed(() => {
    const indicators = this.healthService.healthIndicators();
    if (indicators.length === 0) return 85; // Default healthy

    const latest = indicators[0].healthStatus;
    switch (latest) {
      case HealthStatus.EXCELLENT: return 100;
      case HealthStatus.GOOD: return 80;
      case HealthStatus.FAIR: return 60;
      case HealthStatus.POOR: return 40;
      case HealthStatus.SICK: return 20;
      default: return 85;
    }
  });

  // SVG trend points for health over time
  healthTrendPoints = computed(() => {
    const records = [...this.healthService.healthIndicators()].reverse();
    if (records.length < 2) return '';

    const width = 600;
    const height = 150;
    const step = width / (records.length - 1);
    
    const statusMap: Record<HealthStatus, number> = {
      [HealthStatus.EXCELLENT]: 100,
      [HealthStatus.GOOD]: 80,
      [HealthStatus.FAIR]: 60,
      [HealthStatus.POOR]: 40,
      [HealthStatus.SICK]: 20
    };

    return records.map((r, i) => {
      const x = i * step;
      const y = height - (statusMap[r.healthStatus] / 100 * height);
      return `${x},${y}`;
    }).join(' ');
  });

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.plantId.set(id);
      this.healthService.getHealthByPlant(id).subscribe();
      this.growthService.getMeasurementsByPlant(id).subscribe();
    }
  }

  getStatusLabel(status: HealthStatus): string {
    return status.replace(/_/g, ' ').toLowerCase();
  }
}
