import {inject, Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Register} from "./models/register.model";
import {AccessToken} from "../models/accessToken.model";
import {AuthService} from "@app/auth/auth.service";
import {catchError, tap} from "rxjs/operators";

@Injectable({ providedIn: 'root' })
export class RegisterService {
  private _http: HttpClient = inject(HttpClient);
  private _authService = inject(AuthService);
  public register(credentials:Register): Observable<AccessToken> {
    return this._http.post<AccessToken>(`api/auth/register`, credentials).pipe(
      tap(response => this._authService.handleLoginResponse(response)),
      catchError(error => {
        console.error('Login failed', error);
        throw error;
      })
    )
  }
}
