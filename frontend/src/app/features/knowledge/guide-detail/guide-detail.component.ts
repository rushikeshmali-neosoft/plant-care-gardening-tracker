import { Component, OnInit, ChangeDetectionStrategy, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { KnowledgeService } from '../../../core/services/knowledge.service';
import { CareGuide } from '../../../core/models/knowledge.model';

@Component({
  selector: 'app-guide-detail',
  standalone: true,
  imports: [CommonModule, RouterModule, MatIconModule, MatButtonModule],
  templateUrl: './guide-detail.component.html',
  styleUrls: ['./guide-detail.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CareGuideDetailComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private knowledgeService = inject(KnowledgeService);

  guide = signal<CareGuide | null>(null);
  isLoading = signal(true);

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (id) {
      this.knowledgeService.getGuideById(id).subscribe(data => {
        this.guide.set(data);
        this.isLoading.set(false);
      });
    }
  }
}
