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
    const analysis = this.healthService.healthAnalysis();
    if (!analysis || analysis.indicators.length === 0) return 85; // Default healthy

    return analysis.indicators[0].healthScore;
  });

  // SVG trend points for health over time
  healthTrendPoints = computed(() => {
    const analysis = this.healthService.healthAnalysis();
    if (!analysis || analysis.indicators.length < 2) return '';

    const records = [...analysis.indicators].reverse();
    const width = 600;
    const height = 150;
    const step = width / (records.length - 1);
    
    return records.map((r, i) => {
      const x = i * step;
      const y = height - (r.healthScore / 100 * height);
      return `${x},${y}`;
    }).join(' ');
  });

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.plantId.set(id);
      this.healthService.getFullAnalysis(id).subscribe();
      this.growthService.getMeasurementsByPlant(id).subscribe();
    }
  }

  getStatusLabel(status: HealthStatus): string {
    return status.replace(/_/g, ' ').toLowerCase();
  }
}
