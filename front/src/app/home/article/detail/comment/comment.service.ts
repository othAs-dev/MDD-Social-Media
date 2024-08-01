import { HttpClient } from "@angular/common/http";
import {inject, Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Comments} from "@app/home/article/detail/comment/comment.model";
import {Id} from "@app/shared/models/id.model";

@Injectable({providedIn: 'root'})
export class CommentService {
  private _http: HttpClient = inject(HttpClient);

  public postComment$(articleId: Id, content: string): Observable<Comment> {
    return this._http.post<Comment>(`api/articles/${articleId}/comments`, content);
  }

  public getComments$(articleId: Id): Observable<Comments> {
    return this._http.get<Comments>(`api/articles/${articleId}/comments`);
  }
}
