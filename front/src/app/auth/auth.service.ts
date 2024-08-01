import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import {Login} from "@app/auth/login/models/login.model";

export interface LoginResponse {
  accessToken: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'api/auth';
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(this.hasValidAccessToken());

  constructor(private http: HttpClient, private router: Router) {}

  login(credentials:Login): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap(response => this.handleLoginResponse(response)),
      catchError(error => {
        console.error('Login failed', error);
        throw error;
      })
    );
  }

  private handleLoginResponse(response: LoginResponse): void {
    localStorage.setItem('accessToken', response.accessToken);
    this.isAuthenticatedSubject.next(true);
  }

  logout(): void {
    localStorage.removeItem('accessToken');
    this.isAuthenticatedSubject.next(false);
    this.router.navigate(['/auth/login']);
  }

  getAccessToken(): string | null {
    return localStorage.getItem('accessToken');
  }

  hasValidAccessToken(): boolean {
    return !!this.getAccessToken();
  }

  isAuthenticated(): Observable<boolean> {
    return this.isAuthenticatedSubject.asObservable();
  }
}
