import {inject, Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {Register} from "./models/register.model";
import {AccessToken} from "../models/accessToken.model";
import {CookieService} from "ngx-cookie-service";
import {catchError, switchMap} from "rxjs/operators";

@Injectable({ providedIn: 'root' })
export class RegisterService {
  private _http: HttpClient = inject(HttpClient);
  private readonly _cookieService: CookieService = inject(CookieService);

  public register(credentials: Register): Observable<void> {
    return this._http.post<AccessToken>('api/auth/register', credentials).pipe(
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
