import { Injectable, inject } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Router } from '@angular/router';
import { AccessToken } from "@app/auth/models/accessToken.model";

@Injectable({ providedIn: 'root' })
export class AuthService {
  private _isAuthenticatedSubject = new BehaviorSubject<boolean>(this.hasValidAccessToken());
  private _router: Router = inject(Router);

  /**
   * Handles the login response by storing the access token in local storage
   * and updating the authentication status.
   * @param response The response object containing the access token.
   * @returns void
   */
  public handleLoginResponse(response: AccessToken): void {
    localStorage.setItem('accessToken', response.accessToken);
    this._isAuthenticatedSubject.next(true);
  }

  /**
   * Logs out the current user by removing the access token from local storage
   * and updating the authentication status. Redirects the user to the login page.
   * @returns void
   */
  logout(): void {
    localStorage.removeItem('accessToken');
    this._isAuthenticatedSubject.next(false);
    this._router.navigate(['/auth/login']);
  }

  /**
   * Retrieves the access token from local storage.
   * @returns The access token string if it exists, otherwise null.
   */
  getAccessToken(): string | null {
    return localStorage.getItem('accessToken');
  }

  /**
   * Checks if there is a valid access token in local storage.
   * @returns True if there is a valid access token, otherwise false.
   */
  hasValidAccessToken(): boolean {
    return !!this.getAccessToken();
  }

  /**
   * Returns an observable of the authentication status.
   * @returns An observable that emits the current authentication status (true or false).
   */
  isAuthenticated(): Observable<boolean> {
    return this._isAuthenticatedSubject.asObservable();
  }

  /**
   * Updates the access token in local storage and sets the authentication status to true.
   * @param newToken The new access token to be stored.
   * @returns void
   */
  public updateToken(newToken: string): void {
    console.log('newToken', newToken);
    localStorage.setItem('accessToken', newToken);
    this._isAuthenticatedSubject.next(true);
  }
}
