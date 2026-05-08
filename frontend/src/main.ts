(window as any).global = window;
(window as any).process = { env: { DEBUG: undefined } };

import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';

// Function to hide loading screen and show app
function hideLoadingScreen() {
  const loadingEl = document.getElementById('app-loading');
  if (loadingEl) {
    loadingEl.style.display = 'none';
  }
}

// Function to show error screen
function showErrorScreen(error: any) {
  console.error('Bootstrap error:', error);
  const loadingEl = document.getElementById('app-loading');
  const errorEl = document.getElementById('app-error');
  const errorMessage = document.getElementById('error-message');
  if (loadingEl) loadingEl.style.display = 'none';
  if (errorEl) {
    errorEl.style.display = 'flex';
    if (errorMessage) {
      errorMessage.textContent = error?.message || 'Application failed to load.';
    }
  }
}

// Start bootstrap
bootstrapApplication(AppComponent, appConfig)
  .then(() => {
    // Success: hide loading screen after a short delay to ensure smooth transition
    setTimeout(hideLoadingScreen, 300);
  })
  .catch((err) => {
    showErrorScreen(err);
  });
