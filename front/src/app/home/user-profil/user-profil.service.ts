import { HttpClient } from "@angular/common/http";
import {inject, Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {UserProfil} from "@app/home/user-profil/user-profil.model";
import {Topics} from "@app/home/topic/topic.model";
import {AccessToken} from "@app/auth/models/accessToken.model";

@Injectable({
  providedIn: 'root',
})
export class UserProfilService {
  private _http: HttpClient = inject(HttpClient);
  private token!: string;

  public get(): Observable<UserProfil> {
    return this._http.get<UserProfil>(`api/me`);
  }

  public save(id: string, userProfil: UserProfil): Observable<AccessToken> {
    return this._http.put<AccessToken>(`api/user/${id}`, userProfil);
  }

  public getUserTopics(): Observable<Topics> {
    return this._http.get<Topics>(`api/subscriptions/my-topics`);
  }

    public unsubscribeToTopic(topicId: string): Observable<void> {
    return this._http.get<void>(`api/subscriptions/unsubscribe?topicId=${topicId}`);
  }
}
