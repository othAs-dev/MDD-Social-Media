import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Login } from './models/login.model';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private apiUrl = 'api/auth';

  constructor(private http: HttpClient) {}

  login(credentials: Login): Observable<{ accessToken: string }> {
    return this.http.post<{ accessToken: string }>(`${this.apiUrl}/login`, credentials);
  }

  storeAccessToken(accessToken: string): void {
    localStorage.setItem('accessToken', accessToken);
  }

  getAccessToken(): string | null {
    return localStorage.getItem('accessToken');
  }
}
