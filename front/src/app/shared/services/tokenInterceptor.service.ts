import {inject, Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {BehaviorSubject, catchError, filter, Observable, switchMap, take, throwError} from 'rxjs';
import {AuthService} from "@app/auth/auth.service";

@Injectable({ providedIn: 'root' })
export class TokenInterceptorService implements HttpInterceptor {
  private isRefreshing = false;
  private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);
  private _authService: AuthService = inject(AuthService);

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return this._authService.getAccessToken().pipe(
      switchMap((accessToken: string | null) => {
        if (accessToken) {
          const authReq = this.addToken(req, accessToken);
          return next.handle(authReq).pipe(
            catchError((error: HttpErrorResponse) => {
              if (error.status === 401 && !this.isRefreshing) {
                return this.handle401Error(req, next);
              } else {
                return throwError(error);
              }
            })
          );
        } else {
          return this.handle401Error(req, next);
        }
      })
    );
  }

  private addToken(req: HttpRequest<any>, token: string): HttpRequest<any> {
    return req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  private handle401Error(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.isRefreshing) {
      return this.refreshTokenSubject.pipe(
        filter(token => token != null),
        take(1),
        switchMap(token => next.handle(this.addToken(req, token!)))
      );
    }

    this.isRefreshing = true;
    this.refreshTokenSubject.next(null);

    return this._authService.getAccessToken().pipe(
      switchMap((newToken: string | null) => {
        this.isRefreshing = false;
        if (newToken) {
          this.refreshTokenSubject.next(newToken);
          return next.handle(this.addToken(req, newToken));
        } else {
          this._authService.logout();
          return throwError('No token available');
        }
      }),
      catchError((err) => {
        this.isRefreshing = false;
        this._authService.logout();
        return throwError(err);
      })
    );
  }
}
