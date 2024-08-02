import { HttpClient } from "@angular/common/http";
import {inject, Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {UserProfil} from "@app/home/user-profil/user-profil.model";

@Injectable({
  providedIn: 'root',
})
export class UserProfilService {
  private _http: HttpClient = inject(HttpClient);

  public get(): Observable<UserProfil> {
    return this._http.get<UserProfil>(`api/me`);
  }

  public save(id: string, userProfil: UserProfil): Observable<UserProfil> {
    return this._http.put<UserProfil>(`api/user/${id}`, userProfil);
  }
}
