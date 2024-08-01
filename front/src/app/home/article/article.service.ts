import { HttpClient } from "@angular/common/http";
import {Injectable, inject} from "@angular/core";
import {Articles} from "@app/home/article/article.model";
import {Observable} from "rxjs";

@Injectable({providedIn: 'root'})
export class ArticleService {
  private _http: HttpClient = inject(HttpClient);

  public getArticles(): Observable<Articles> {
    return this._http.get<Articles>('api/articles/my-articles');
  }
}
