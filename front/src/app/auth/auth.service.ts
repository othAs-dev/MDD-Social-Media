import { Injectable, inject } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Router } from '@angular/router';
import {AccessToken} from "@app/auth/models/accessToken.model";

@Injectable({ providedIn: 'root' })
export class AuthService {
  private _isAuthenticatedSubject = new BehaviorSubject<boolean>(this.hasValidAccessToken());
  private _router: Router = inject(Router);

  public handleLoginResponse(response: AccessToken): void {
    localStorage.setItem('accessToken', response.accessToken);
    this._isAuthenticatedSubject.next(true);
  }

  logout(): void {
    localStorage.removeItem('accessToken');
    this._isAuthenticatedSubject.next(false);
    this._router.navigate(['/auth/login']);
  }

  getAccessToken(): string | null {
    return localStorage.getItem('accessToken');
  }

  hasValidAccessToken(): boolean {
    return !!this.getAccessToken();
  }

  isAuthenticated(): Observable<boolean> {
    return this._isAuthenticatedSubject.asObservable();
  }

  public updateToken(newToken: string): void {
    console.log('newToken', newToken)
    localStorage.setItem('accessToken', newToken);
    this._isAuthenticatedSubject.next(true);
  }
}
