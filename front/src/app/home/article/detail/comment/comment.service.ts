import { HttpClient } from "@angular/common/http";
import {inject, Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Comments} from "@app/home/article/detail/comment/comment.model";
import {Id} from "@app/shared/models/id.model";

@Injectable({providedIn: 'root'})
export class CommentService {
  private _http: HttpClient = inject(HttpClient);

  /**
   * @description Post comment send a POST request to the comments API endpoint with the article ID and the comment content.
   * @param articleId
   * @param content
   * @returns Observable<Comment>
   * */
  public postComment$(articleId: Id, content: string): Observable<Comment> {
    return this._http.post<Comment>(`api/articles/${articleId}/comments`, content);
  }

  /**
   * @description Get comments send a GET request to the comments API endpoint with the article ID.
   * @param articleId
   * @returns Observable<Comments>
   * */
  public getComments$(articleId: Id): Observable<Comments> {
    return this._http.get<Comments>(`api/articles/${articleId}/comments`);
  }
}
