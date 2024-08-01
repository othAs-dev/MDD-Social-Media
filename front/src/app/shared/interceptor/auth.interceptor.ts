import { inject } from '@angular/core';
import { HttpInterceptorFn, HttpRequest, HttpHandlerFn, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from '@app/auth/auth.service';

export const authInterceptor: HttpInterceptorFn = (
  request: HttpRequest<unknown>,
  next: HttpHandlerFn
): Observable<HttpEvent<unknown>> => {
  const authService = inject(AuthService);

  const accessToken = authService.getAccessToken();

  let clonedRequest = request;
  if (accessToken) {
    clonedRequest = request.clone({
      headers: request.headers.set('Authorization', `Bearer ${accessToken}`)
    });
  }

  return next(clonedRequest).pipe(
    catchError(error => {
      if (error instanceof HttpErrorResponse && error.status === 401) {
        authService.logout();
      }
      return throwError(() => error);
    })
  );
};
