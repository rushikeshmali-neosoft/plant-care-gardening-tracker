import { Component, OnInit, ChangeDetectionStrategy, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatChipsModule } from '@angular/material/chips';
import { KnowledgeService } from '../../../core/services/knowledge.service';

@Component({
  selector: 'app-guide-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatIconModule,
    MatButtonModule,
    MatInputModule,
    MatFormFieldModule,
    MatChipsModule
  ],
  templateUrl: './guide-list.component.html',
  styleUrls: ['./guide-list.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CareGuideListComponent implements OnInit {
  knowledgeService = inject(KnowledgeService);
  selectedCategory = signal<string>('All');

  categories = ['All', 'Indoor', 'Outdoor', 'Succulents', 'Edibles', 'Flowers'];

  ngOnInit(): void {
    this.knowledgeService.getAllGuides().subscribe();
  }

  onSearch(event: Event): void {
    const query = (event.target as HTMLInputElement).value;
    if (query.length >= 3) {
      this.knowledgeService.searchGuides(query).subscribe();
    } else if (query.length === 0) {
      this.knowledgeService.getAllGuides().subscribe();
    }
  }

  setCategory(cat: string): void {
    this.selectedCategory.set(cat);
    // In a real app, this would filter via API or locally
  }
}
