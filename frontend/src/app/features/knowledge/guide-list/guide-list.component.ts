import { Component, OnInit, ChangeDetectionStrategy, inject, signal, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatChipsModule } from '@angular/material/chips';
import { KnowledgeService } from '../../../core/services/knowledge.service';
import { Subject, Subscription, debounceTime, distinctUntilChanged } from 'rxjs';

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
export class CareGuideListComponent implements OnInit, OnDestroy {
  knowledgeService = inject(KnowledgeService);
  selectedCategory = signal<string>('All');

  categories = ['All', 'Care Guides', 'Problem Solutions', 'Seasonal Tips', 'Plant Database', 'User Tips'];

  private searchSubject = new Subject<string>();
  private searchSubscription: Subscription;

  constructor() {
    this.searchSubscription = this.searchSubject
      .pipe(
        debounceTime(300),
        distinctUntilChanged()
      )
      .subscribe(query => {
        const trimmedQuery = query.trim();
        if (trimmedQuery.length >= 3) {
          this.knowledgeService.searchGuides(trimmedQuery).subscribe();
        } else if (trimmedQuery.length === 0) {
          this.knowledgeService.getAllGuides().subscribe();
        }
      });
  }

  ngOnInit(): void {
    this.knowledgeService.getAllGuides().subscribe();
  }

  ngOnDestroy(): void {
    this.searchSubscription.unsubscribe();
  }

  onSearch(event: Event): void {
    const query = (event.target as HTMLInputElement).value;
    this.searchSubject.next(query);
  }

  setCategory(cat: string): void {
    this.selectedCategory.set(cat);
    this.knowledgeService.getAllGuides(cat).subscribe();
  }
}
