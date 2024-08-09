import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { UpdateUserProfil, UserProfil } from "@app/home/user-profil/user-profil.model";
import { Topics } from "@app/home/topic/topic.model";

@Injectable({
  providedIn: 'root',
})
export class UserProfilService {
  private _http: HttpClient = inject(HttpClient);

  /**
   * Retrieves the profile of the currently authenticated user.
   * Sends a GET request to the user profile API endpoint and returns the observable.
   * @returns An observable that emits the user profile.
   */
  public get(): Observable<UserProfil> {
    return this._http.get<UserProfil>(`api/me`);
  }

  /**
   * Updates the profile information of a user.
   * Sends a PUT request to the user profile API endpoint with the updated profile data.
   * @param id The ID of the user whose profile is to be updated.
   * @param userProfil The updated user profile data.
   * @returns An observable that emits the result of the update operation.
   */
  public save(id: string, userProfil: Partial<UserProfil>): Observable<UpdateUserProfil> {
    return this._http.put<UpdateUserProfil>(`api/user/${id}`, userProfil);
  }

  /**
   * Retrieves the topics that the currently authenticated user is subscribed to.
   * Sends a GET request to the API endpoint for subscribed topics and returns the observable.
   * @returns An observable that emits the list of topics the user is subscribed to.
   */
  public getUserTopics(): Observable<Topics> {
    return this._http.get<Topics>(`api/subscriptions/my-topics`);
  }

  /**
   * Unsubscribes the currently authenticated user from a specific topic.
   * Sends a GET request to the subscription API endpoint with the topic ID as a query parameter.
   * @param topicId The ID of the topic to which the user wants to unsubscribe.
   * @returns An observable that completes when the unsubscription is successful.
   */
  public unsubscribeToTopic(topicId: string): Observable<void> {
    return this._http.get<void>(`api/subscriptions/unsubscribe?topicId=${topicId}`);
  }
}
