import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { AuthService } from '../services/auth.service';

export interface ErrorResponse {
  message: string;
  status: number;
  timestamp: Date;
}

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      if ([401, 403].includes(error.status)) {
        authService.logout();
        router.navigate(['/auth/login']);
      }

      const errorResponse: ErrorResponse = {
        message: error.error?.message || error.statusText || 'Unknown error',
        status: error.status,
        timestamp: new Date()
      };
      console.error('HTTP Error:', errorResponse);
      return throwError(() => errorResponse);
    })
  );
};
