import {inject, Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Login} from "./models/login.model";
import {AccessToken} from "../models/accessToken.model";

@Injectable({ providedIn: 'root' })
export class LoginService {
  private _http: HttpClient = inject(HttpClient);

  public login(credentials: Login): Observable<AccessToken> {
    return this._http.post<AccessToken>('api/auth/login', credentials);
  }
}
