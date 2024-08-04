import {Component, inject, OnDestroy} from '@angular/core';
import {TopicService} from '@app/home/topic/topic.service';
import {Observable, startWith, Subject, switchMap, take} from 'rxjs';
import {Topics} from '@app/home/topic/topic.model';
import {AsyncPipe} from '@angular/common';
import {catchError} from 'rxjs/operators';
import {MatButton} from '@angular/material/button';
import {TopicCardComponent} from '@app/shared/components/topic-card/topic-card.component';
import {Id} from '@app/shared/models/id.model';
import {MatSnackBar} from '@angular/material/snack-bar';
import {CardArticleComponent} from "@app/home/article/card-article/card-article.component";

@Component({
  selector: 'app-topic',
  standalone: true,
  imports: [
    AsyncPipe,
    MatButton,
    TopicCardComponent,
    CardArticleComponent
  ],
  templateUrl: './topic.component.html',
  styleUrls: ['./topic.component.scss']
})
export default class TopicComponent implements OnDestroy {
  private readonly _topicService: TopicService = inject(TopicService);
  private _matSnackBar: MatSnackBar = inject(MatSnackBar);

  /**
   * Subject to trigger a refresh of the topics list.
   */
  protected refresh$ = new Subject<void>();

  /**
   * Observable that emits the list of topics.
   * Refreshes the topics list whenever `refresh$` emits.
   */
  protected topics$: Observable<Topics> = this.refresh$.pipe(
    startWith(null),
    switchMap(() => this._topicService.getTopics()),
    catchError(() => {
      this._matSnackBar.open('Une erreur est survenue, veuillez réessayer plus tard.', 'Fermer');
      return [];
    })
  );

  /**
   * Subscribes the user to a specific topic.
   * Refreshes the topics list and displays a confirmation message upon successful subscription.
   * @param topicId The ID of the topic to subscribe to.
   * @param topicName The name of the topic for display purposes.
   */
  protected subscribeToTopic(topicId: Id, topicName: string): void {
    this._topicService.subscribeToTopic(topicId).pipe(take(1)).subscribe({
      next: () => {
        this.refresh$.next();
        this._matSnackBar.open(`Vous êtes désormais abonné à ${topicName}`, 'Fermer');
      },
      error: () => this._matSnackBar.open('Une erreur est survenue, veuillez réessayer plus tard.', 'Fermer')
    });
  }

  /**
   * Cleans up any resources when the component is destroyed.
   * Dismisses any open snack bar notifications.
   */
  ngOnDestroy(): void {
    this._matSnackBar.dismiss();
  }
}
