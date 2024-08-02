import { HttpClient } from "@angular/common/http";
import {Injectable, inject} from "@angular/core";
import {Observable} from "rxjs";
import {Topics} from "@app/home/topic/topic.model";

@Injectable({providedIn : 'root'})
export class TopicService {
  private _http: HttpClient = inject(HttpClient);

  public getTopics(): Observable<Topics> {
    return this._http.get<Topics>('api/topics');
  }

  public subscribeToTopic(topicId: string): Observable<void> {
    return this._http.get<void>(`api/subscriptions/subscribe?topicId=${topicId}`);
  }
}
