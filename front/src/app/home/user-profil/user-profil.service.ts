import { HttpClient } from "@angular/common/http";
import {inject, Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {UserProfil} from "@app/home/user-profil/user-profil.model";

@Injectable({
  providedIn: 'root',
})
export class UserProfilService {
  private _http: HttpClient = inject(HttpClient);

  public getUserProfil(): Observable<UserProfil> {
    return this._http.get<UserProfil>(`api/auth/me`);
  }
}
