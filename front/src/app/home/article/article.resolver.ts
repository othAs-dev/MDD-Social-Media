import { ResolveFn, Router } from "@angular/router";
import { inject } from "@angular/core";
import { catchError, EMPTY, take } from "rxjs";
import { HttpClient } from "@angular/common/http";
import {Article} from "@app/home/article/article.model";


/**
 *
 * @description Resolver for Article
 *
 * @returns Article
 *
 **/
export const ArticleResolver: ResolveFn<Article> = (
  route,
  state,
  router = inject(Router)
) =>
  inject(HttpClient)
    .get<Article>(`api/articles/${route.params["articleId"]}`)
    .pipe(
      take(1),
      catchError((err) => {
        router.navigate(["/articles"]);
        return EMPTY;
      })
    );
