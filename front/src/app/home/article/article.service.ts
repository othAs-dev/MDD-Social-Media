import { HttpClient } from "@angular/common/http";
import {Injectable, inject} from "@angular/core";
import {Articles} from "@app/home/article/article.model";
import {Observable} from "rxjs";

@Injectable({providedIn: 'root'})
export class ArticleService {
  private _http: HttpClient = inject(HttpClient);

  /**
   * @description Get articles
   * Sends a GET request to the articles API endpoint and returns the observable.
   * @returns An observable that emits the list of articles.
   */
  public getArticles(): Observable<Articles> {
    return this._http.get<Articles>('api/articles/my-articles');
  }
}
