import { Component, OnInit, ChangeDetectionStrategy, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTabsModule } from '@angular/material/tabs';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { PlantService } from '../../../core/services/plant.service';
import { Plant, PlantStatus } from '../../../core/models/plant.model';

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
  isArchiving = signal(false);

  private debugLog(runId: string, hypothesisId: string, location: string, message: string, data: Record<string, unknown>): void {
    // #region agent log
    fetch('http://127.0.0.1:7254/ingest/0743ddfc-4204-4a8c-bf46-2bb28c23616f',{method:'POST',headers:{'Content-Type':'application/json','X-Debug-Session-Id':'3d5f7b'},body:JSON.stringify({sessionId:'3d5f7b',runId,hypothesisId,location,message,data,timestamp:Date.now()})}).catch(()=>{});
    // #endregion
  }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.debugLog('initial', 'H2', 'plant-detail.component.ts:ngOnInit', 'Plant detail route id parsed', {
      routeIdRaw: this.route.snapshot.paramMap.get('id'),
      parsedId: id
    });
    if (id) {
      this.plantService.getPlantById(id).subscribe({
        next: (plant) => {
          this.plant.set(plant);
          this.isLoading.set(false);
          this.debugLog('initial', 'H3', 'plant-detail.component.ts:ngOnInit:next', 'Plant loaded for details page', {
            plantId: plant.id,
            status: plant.status,
            commonName: plant.commonName
          });
        },
        error: (error) => {
          this.isLoading.set(false);
          this.debugLog('initial', 'H3', 'plant-detail.component.ts:ngOnInit:error', 'Plant details load failed', {
            status: error?.status ?? null,
            message: error?.message ?? 'unknown'
          });
        }
      });
    }
  }

  onEditDetails(): void {
    const plant = this.plant();
    this.debugLog('initial', 'H1', 'plant-detail.component.ts:onEditDetails', 'Edit details clicked', {
      hasPlant: !!plant,
      plantId: plant?.id ?? null
    });
  }

  onArchivePlant(): void {
    const plant = this.plant();
    this.debugLog('initial', 'H1', 'plant-detail.component.ts:onArchivePlant', 'Archive plant clicked', {
      hasPlant: !!plant,
      plantId: plant?.id ?? null,
      currentStatus: plant?.status ?? null
    });

    if (!plant || this.isArchiving()) {
      return;
    }

    this.isArchiving.set(true);
    const updateRequest = {
      commonName: plant.commonName,
      scientificName: plant.scientificName,
      species: plant.species,
      variety: plant.variety,
      purchaseDate: plant.purchaseDate,
      source: plant.source,
      locationType: plant.locationType,
      roomGarden: plant.roomGarden,
      status: PlantStatus.ARCHIVED
    } as const;

    this.debugLog('initial', 'H4', 'plant-detail.component.ts:onArchivePlant:beforeRequest', 'Archive update request prepared', {
      plantId: plant.id,
      requestStatus: updateRequest.status,
      hasCommonName: !!updateRequest.commonName
    });

    this.plantService.updatePlant(plant.id, updateRequest).subscribe({
      next: (updatedPlant) => {
        this.plant.set(updatedPlant);
        this.isArchiving.set(false);
        this.debugLog('initial', 'H4', 'plant-detail.component.ts:onArchivePlant:next', 'Archive update request succeeded', {
          plantId: updatedPlant.id,
          updatedStatus: updatedPlant.status
        });
      },
      error: (error) => {
        this.isArchiving.set(false);
        this.debugLog('initial', 'H4', 'plant-detail.component.ts:onArchivePlant:error', 'Archive update request failed', {
          status: error?.status ?? null,
          message: error?.message ?? 'unknown'
        });
      }
    });
  }

  onArchiveInteraction(eventType: string): void {
    const plant = this.plant();
    this.debugLog('initial', 'H5', 'plant-detail.component.ts:onArchiveInteraction', 'Archive interaction event captured', {
      eventType,
      hasPlant: !!plant,
      plantId: plant?.id ?? null,
      isArchiving: this.isArchiving()
    });
  }

  getHealthScore(): number { return 88; } // Mock data
}
