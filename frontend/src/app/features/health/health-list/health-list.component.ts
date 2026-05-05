import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { PlantService } from '../../../core/services/plant.service';
import { HealthService } from '../../../core/services/health.service';
import { Plant } from '../../../core/models/plant.model';

@Component({
  selector: 'app-health-list',
  standalone: true,
  imports: [CommonModule, RouterModule, MatIconModule, MatButtonModule],
  template: `
    <div class="health-list-container fade-in">
      <header class="page-header">
        <div>
          <h1>Plants Health Overview</h1>
          <p class="subtitle">Real-time vitality status across your garden</p>
        </div>
      </header>

      <div class="health-grid">
        <div *ngFor="let plant of plantService.plants()" 
             class="plant-health-card glass-card"
             [routerLink]="['/plants', plant.id, 'health']">
          <div class="card-top">
            <div class="plant-info">
              <h3>{{ plant.commonName }}</h3>
              <p>{{ plant.scientificName }}</p>
            </div>
            <div class="health-status-badge" [attr.data-status]="plant.status">
              {{ plant.status }}
            </div>
          </div>
          
          <div class="card-footer">
            <span class="view-details">View Diagnostic Analysis</span>
            <mat-icon>chevron_right</mat-icon>
          </div>
        </div>
      </div>

      <div *ngIf="plantService.plants().length === 0 && !plantService.isLoading()" class="empty-state">
        <mat-icon>health_and_safety</mat-icon>
        <p>No plants found. Add some plants to monitor their health.</p>
        <button mat-flat-button color="primary" routerLink="/plants">Go to My Plants</button>
      </div>
    </div>
  `,
  styles: [`
    .health-list-container {
      padding: 40px;
      max-width: 1400px;
      margin: 0 auto;
    }
    .page-header {
      margin-bottom: 32px;
    }
    .page-header h1 {
      font-size: 32px;
      margin: 0;
    }
    .subtitle {
      color: var(--text-muted);
      margin-top: 4px;
    }
    .health-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
      gap: 24px;
    }
    .plant-health-card {
      padding: 24px;
      cursor: pointer;
      transition: transform 0.2s, box-shadow 0.2s;
    }
    .plant-health-card:hover {
      transform: translateY(-4px);
      box-shadow: var(--shadow-lg);
    }
    .card-top {
      display: flex;
      justify-content: space-between;
      align-items: flex-start;
      margin-bottom: 24px;
    }
    .plant-info h3 {
      margin: 0;
      font-size: 18px;
    }
    .plant-info p {
      margin: 4px 0 0 0;
      font-size: 13px;
      color: var(--text-muted);
      font-style: italic;
    }
    .health-status-badge {
      padding: 4px 12px;
      border-radius: 20px;
      font-size: 12px;
      font-weight: 600;
      background: var(--primary-50);
      color: var(--primary-color);
    }
    .card-footer {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding-top: 16px;
      border-top: 1px solid var(--border-color);
      color: var(--primary-color);
      font-weight: 500;
      font-size: 14px;
    }
    .empty-state {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      padding: 80px 20px;
      gap: 16px;
      color: var(--text-muted);
    }
    .empty-state mat-icon {
      font-size: 64px;
      width: 64px;
      height: 64px;
      opacity: 0.5;
    }
  `]
})
export class HealthListComponent implements OnInit {
  plantService = inject(PlantService);
  healthService = inject(HealthService);

  ngOnInit(): void {
    this.plantService.getPlants().subscribe();
  }
}
