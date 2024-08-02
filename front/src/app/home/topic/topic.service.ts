import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { Observable } from "rxjs";
import { Topics } from "@app/home/topic/topic.model";

@Injectable({ providedIn: 'root' })
export class TopicService {
  private _http: HttpClient = inject(HttpClient);

  /**
   * Retrieves a list of topics from the API.
   * Sends a GET request to the topics API endpoint and returns the observable.
   * @returns An observable that emits the list of topics.
   */
  public getTopics(): Observable<Topics> {
    return this._http.get<Topics>('api/topics');
  }

  /**
   * Subscribes the current user to a specific topic.
   * Sends a GET request to the subscription API endpoint with the topic ID as a query parameter.
   * @param topicId The ID of the topic to which the user wants to subscribe.
   * @returns An observable that completes when the subscription is successful.
   */
  public subscribeToTopic(topicId: string): Observable<void> {
    return this._http.get<void>(`api/subscriptions/subscribe?topicId=${topicId}`);
  }
}
