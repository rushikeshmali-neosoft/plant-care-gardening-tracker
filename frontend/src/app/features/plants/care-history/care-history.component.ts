import { Component, OnInit, ChangeDetectionStrategy, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { LogService } from '../../../core/services/log.service';
import { CareType } from '../../../core/models/care.model';

@Component({
  selector: 'app-care-history',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatIconModule,
    MatButtonModule
  ],
  templateUrl: './care-history.component.html',
  styleUrls: ['./care-history.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CareHistoryComponent implements OnInit {
  private route = inject(ActivatedRoute);
  logService = inject(LogService);

  plantId = signal<number | null>(null);

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.plantId.set(id);
      this.logService.getLogsByPlant(id).subscribe();
    }
  }

  getCareIcon(type: CareType): string {
    switch (type) {
      case CareType.WATERING: return 'water_drop';
      case CareType.FERTILIZING: return 'eco';
      case CareType.PRUNING: return 'content_cut';
      case CareType.REPOTTING: return 'layers';
      default: return 'event';
    }
  }

  getCareClass(type: CareType): string {
    return type.toLowerCase();
  }
}
