import { Injectable, inject } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Register } from "./models/register.model";
import { AccessToken } from "../models/accessToken.model";
import { AuthService } from "@app/auth/auth.service";
import { catchError, tap } from "rxjs/operators";

@Injectable({ providedIn: 'root' })
export class RegisterService {
  private _http: HttpClient = inject(HttpClient);
  private _authService = inject(AuthService);

  /**
   * Registers a new user with the provided credentials.
   * Sends a POST request to the registration API endpoint and handles the response.
   * @param credentials The registration details provided by the user.
   * @returns An observable that emits the access token upon successful registration.
   */
  public register(credentials: Register): Observable<AccessToken> {
    return this._http.post<AccessToken>(`api/auth/register`, credentials).pipe(
      tap(response => this._authService.handleLoginResponse(response)),
      catchError(error => {
        console.error('Registration failed', error);
        throw error;
      })
    );
  }
}
