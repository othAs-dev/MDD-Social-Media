import {Injectable, inject} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Article} from "@app/home/article/article.model";
import {Observable} from "rxjs";

@Injectable({providedIn: 'root'})
export class CreateArticleService {
  private _http: HttpClient = inject(HttpClient);

  createArticle(article: Article): Observable<Article> {
    return this._http.post<Article>('api/articles', article);
  }
}
