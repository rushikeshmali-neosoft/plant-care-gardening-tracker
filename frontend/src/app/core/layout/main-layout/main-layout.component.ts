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
      background: #0f1410; /* Deep botanical dark background */
      background-image: 
        radial-gradient(circle at 0% 0%, rgba(45, 90, 39, 0.15) 0%, transparent 50%),
        radial-gradient(circle at 100% 100%, rgba(139, 168, 136, 0.1) 0%, transparent 50%);
      color: white;
      overflow: hidden;
    }

    .main-content {
      flex: 1;
      display: flex;
      flex-direction: column;
      overflow: hidden;
    }

    .page-content {
      flex: 1;
      padding: 0;
      overflow-y: auto;
      scrollbar-width: thin;
      scrollbar-color: var(--primary-color) transparent;
    }

    .page-content::-webkit-scrollbar {
      width: 6px;
    }

    .page-content::-webkit-scrollbar-thumb {
      background: var(--primary-color);
      border-radius: 10px;
    }
  `],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class MainLayoutComponent {}
