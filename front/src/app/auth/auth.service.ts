import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly _http: HttpClient = inject(HttpClient);
  private readonly _cookieService: CookieService = inject(CookieService);
  private readonly _router: Router = inject(Router);
  public logout(): void {
    localStorage.removeItem('accessToken');
    this._cookieService.delete('refreshToken');
    this._router.navigate(['/auth']);
  }

  public getAccessToken(): Observable<string | null> {
    const accessToken = localStorage.getItem('accessToken');
    if (accessToken) {
      return of(accessToken);
    }

    const refreshToken = this._cookieService.get('refreshToken');
    if (refreshToken) {
      return this._http.post<{ accessToken: string }>('api/auth/refresh-token', { refreshToken })
        .pipe(
          switchMap(response => {
            localStorage.setItem('accessToken', response.accessToken);
            return of(response.accessToken);
          }),
          catchError(() => {
            this.logout();
            return of(null);
          })
        );
    } else {
      return of(null);
    }
  }
}
