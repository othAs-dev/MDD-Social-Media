import {HttpClient} from "@angular/common/http";
import {inject, Injectable} from "@angular/core";

@Injectable({providedIn: 'root'})
export class AuthService {

  private _http: HttpClient = inject(HttpClient);

  public isAuthenticated(): boolean {
    const token = sessionStorage.getItem('token');
    return !!token;
  }

}
