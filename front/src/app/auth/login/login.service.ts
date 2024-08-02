import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Login } from './models/login.model';
import { AccessToken } from "@app/auth/models/accessToken.model";
import { catchError, tap } from "rxjs/operators";
import { AuthService } from "@app/auth/auth.service";

@Injectable({ providedIn: 'root' })
export class LoginService {
  private _http: HttpClient = inject(HttpClient);
  private _authService = inject(AuthService);

  /**
   * Authenticates a user with the provided credentials.
   * Sends a POST request to the login API endpoint and handles the response.
   * @param credentials The login credentials provided by the user.
   * @returns An observable that emits the access token upon successful login.
   */
  login(credentials: Login): Observable<AccessToken> {
    return this._http.post<AccessToken>(`api/auth/login`, credentials).pipe(

      tap(response => this._authService.handleLoginResponse(response)),
      catchError(error => {
        console.error('Login failed', error);
        throw error;
      })
    );
  }
}
