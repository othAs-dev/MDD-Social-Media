import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Login } from './models/login.model';
import { catchError, switchMap } from 'rxjs/operators';
import { CookieService } from 'ngx-cookie-service';
import {AccessToken} from "@app/auth/models/accessToken.model";

@Injectable({ providedIn: 'root' })
export class LoginService {
  private _http: HttpClient = inject(HttpClient);
  private readonly _cookieService: CookieService = inject(CookieService);

  public login(credentials: Login): Observable<void> {
    return this._http.post<AccessToken>('api/auth/login', credentials).pipe(
      switchMap(response => {
        localStorage.setItem('accessToken', response.accessToken);
        this._cookieService.set('refreshToken', response.refreshToken, 1, '/');
        return of(void 0);
      }),
      catchError(error => {
        console.error('Login failed', error);
        return of(void 0);
      })
    );
  }
}
