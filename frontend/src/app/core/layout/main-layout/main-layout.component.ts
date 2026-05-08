import { Component, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { SidebarComponent } from '../sidebar/sidebar.component';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-main-layout',
  standalone: true,
  imports: [CommonModule, RouterModule, SidebarComponent, HeaderComponent],
  template: `
    <div class="layout-container">
      <app-sidebar></app-sidebar>
      <div class="main-content">
        <app-header></app-header>
        <main class="page-content">
          <router-outlet></router-outlet>
        </main>
      </div>
    </div>
  `,
  styles: [`
    .layout-container {
      display: flex;
      height: 100vh;
      width: 100vw;
      background: transparent;
      color: var(--text-main);
      overflow: hidden;
    }

    .main-content {
      flex: 1;
      display: flex;
      flex-direction: column;
      overflow: hidden;
      background: transparent;
    }

    .page-content {
      flex: 1;
      padding: 16px 32px 32px 16px;
      overflow-y: auto;
      scrollbar-width: thin;
      scrollbar-color: var(--primary-100) transparent;
    }

    .page-content::-webkit-scrollbar {
      width: 6px;
    }

    .page-content::-webkit-scrollbar-thumb {
      background: var(--primary-100);
      border-radius: 10px;
    }

    .page-content::-webkit-scrollbar-thumb:hover {
      background: var(--primary-light);
    }
  `],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class MainLayoutComponent {}
