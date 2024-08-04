import {Component, inject, OnDestroy, OnInit} from '@angular/core';
import {AsyncPipe, NgForOf} from '@angular/common';
import {MatButton, MatFabButton} from '@angular/material/button';
import {TopicCardComponent} from '@app/shared/components/topic-card/topic-card.component';
import {CardArticleComponent} from '@app/home/article/card-article/card-article.component';
import {BehaviorSubject, Observable, Subscription, take, tap} from 'rxjs';
import {Articles} from '@app/home/article/article.model';
import {ArticleService} from '@app/home/article/article.service';
import {RouterLink} from '@angular/router';
import {MatIcon} from '@angular/material/icon';

@Component({
  selector: 'app-article',
  standalone: true,
  imports: [
    NgForOf,
    MatButton,
    AsyncPipe,
    TopicCardComponent,
    CardArticleComponent,
    RouterLink,
    MatFabButton,
    MatIcon
  ],
  templateUrl: './article.component.html',
  styles: []
})
export default class ArticleComponent implements OnInit, OnDestroy {
  private readonly _articleService: ArticleService = inject(ArticleService);
  protected sortedArticles$: BehaviorSubject<Articles> = new BehaviorSubject<Articles>([]);
  protected isAsc: boolean = true;
  private articlesSubscription: Subscription = new Subscription();
  protected articles$: Observable<Articles> = this._articleService.getArticles().pipe(
    tap(articles => this.sortedArticles$.next(articles))
  );

  ngOnInit() { this.articlesSubscription.add(this.articles$.subscribe()) }

  protected changeSortOrder(): void {
    const order = this.isAsc ? 'desc' : 'asc';
    this.isAsc = !this.isAsc;
    this.articles$.pipe(
      take(1),
      tap(articles => {
        articles.sort((a, b) => {
          const dateA = new Date(a.createdAt).getTime();
          const dateB = new Date(b.createdAt).getTime();
          return order === 'asc' ? dateA - dateB : dateB - dateA;
        });
        this.sortedArticles$.next(articles);
      })
    ).subscribe();
  }

  ngOnDestroy() {
    this.articlesSubscription.unsubscribe()
  }
}
