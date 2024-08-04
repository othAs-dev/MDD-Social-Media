import {Component, inject} from '@angular/core';
import {AsyncPipe, NgForOf} from "@angular/common";
import {MatButton} from "@angular/material/button";
import {TopicCardComponent} from "@app/shared/components/topic-card/topic-card.component";
import {CardArticleComponent} from "@app/home/article/card-article/card-article.component";
import {Observable} from "rxjs";
import {Articles} from "@app/home/article/article.model";
import {ArticleService} from "@app/home/article/article.service";
import {RouterLink} from "@angular/router";
import {tap} from "rxjs/operators";

@Component({
  selector: 'app-article',
  standalone: true,
  imports: [
    NgForOf,
    MatButton,
    AsyncPipe,
    TopicCardComponent,
    CardArticleComponent,
    RouterLink
  ],
  templateUrl: './article.component.html',
  styles: []
})
export default class ArticleComponent {
  private readonly _articleService: ArticleService = inject(ArticleService);

  /**
   *
   * @description Observable of articles
   *
   * @example
   * articles$;
   *
   * @returns Observable<Articles
   *
   **/
  protected articles$: Observable<Articles> = this._articleService.getArticles();
}
