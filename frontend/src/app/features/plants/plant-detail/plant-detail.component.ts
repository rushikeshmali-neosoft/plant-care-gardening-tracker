import { Component, OnInit, ChangeDetectionStrategy, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTabsModule } from '@angular/material/tabs';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { PlantService } from '../../../core/services/plant.service';
import { Plant } from '../../../core/models/plant.model';

@Component({
  selector: 'app-plant-detail',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatIconModule,
    MatButtonModule,
    MatTabsModule,
    MatProgressBarModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './plant-detail.component.html',
  styleUrls: ['./plant-detail.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PlantDetailComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private plantService = inject(PlantService);

  plant = signal<Plant | null>(null);
  isLoading = signal(true);

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.plantService.getPlantById(id).subscribe({
        next: (plant) => {
          this.plant.set(plant);
          this.isLoading.set(false);
        },
        error: () => this.isLoading.set(false)
      });
    }
  }

  getHealthScore(): number { return 88; } // Mock data
}
