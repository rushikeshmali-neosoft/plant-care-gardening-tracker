import { Component, OnInit, ChangeDetectionStrategy, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { GrowthService } from '../../../core/services/growth.service';
import { PlantMeasurement } from '../../../core/models/growth.model';

@Component({
  selector: 'app-growth-history',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatIconModule,
    MatButtonModule,
    MatTableModule
  ],
  templateUrl: './growth-history.component.html',
  styleUrls: ['./growth-history.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class GrowthHistoryComponent implements OnInit {
  private route = inject(ActivatedRoute);
  growthService = inject(GrowthService);

  plantId = signal<number | null>(null);
  displayedColumns: string[] = ['date', 'height', 'width', 'notes'];

  // Trend calculation for simple SVG chart
  trendPoints = computed(() => {
    const measurements = [...this.growthService.measurements()].reverse();
    if (measurements.length < 2) return '';

    const maxVal = Math.max(...measurements.map(m => m.heightCm || 0), 1);
    const width = 400;
    const height = 100;
    const step = width / (measurements.length - 1);

    return measurements.map((m, i) => {
      const x = i * step;
      const y = height - ((m.heightCm || 0) / maxVal * height);
      return `${x},${y}`;
    }).join(' ');
  });

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.plantId.set(id);
      this.growthService.getMeasurementsByPlant(id).subscribe();
    }
  }

  onAddMeasurement(): void {
    // Dialog will be implemented next
    console.log('Add Measurement');
  }
}
