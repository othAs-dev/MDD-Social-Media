import {inject, Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Register} from "./models/register.model";
import {AccessToken} from "../models/accessToken.model";

@Injectable({ providedIn: 'root' })
export class RegisterService {
  private _http: HttpClient = inject(HttpClient);

  public register(credentials: Register): Observable<AccessToken> {
    return this._http.post<AccessToken>('api/auth/register', credentials);
  }
}
