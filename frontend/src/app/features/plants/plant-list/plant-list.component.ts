import { Component, OnInit, ChangeDetectionStrategy, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatChipsModule } from '@angular/material/chips';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { RouterModule } from '@angular/router';
import { PlantService } from '../../../core/services/plant.service';
import { Plant, PlantStatus } from '../../../core/models/plant.model';
import { AddPlantComponent } from '../add-plant/add-plant.component';

@Component({
  selector: 'app-plant-list',
  standalone: true,
  imports: [
    CommonModule,
    MatIconModule,
    MatButtonModule,
    MatProgressBarModule,
    MatChipsModule,
    MatDialogModule,
    RouterModule
  ],
  templateUrl: './plant-list.component.html',
  styleUrls: ['./plant-list.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PlantListComponent implements OnInit {
  plantService = inject(PlantService);
  private dialog = inject(MatDialog);

  ngOnInit(): void {
    this.plantService.getPlants().subscribe();
  }

  openAddPlantDialog(): void {
    const dialogRef = this.dialog.open(AddPlantComponent, {
      width: '600px',
      panelClass: 'custom-dialog-container'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.plantService.getPlants().subscribe();
      }
    });
  }

  getStatusColor(status: PlantStatus): string {
    switch (status) {
      case PlantStatus.ACTIVE: return 'primary-glow';
      case PlantStatus.DORMANT: return 'dormant-glow';
      case PlantStatus.PROPAGATING: return 'prop-glow';
      default: return '';
    }
  }

  getHealthScore(plant: Plant): number {
    // Mock health score logic
    return 85; 
  }
}
