import {Component, inject, OnDestroy} from '@angular/core';
import {TopicService} from "@app/home/topic/topic.service";
import {Observable} from "rxjs";
import {Topics} from "@app/home/topic/topic.model";
import {AsyncPipe} from "@angular/common";
import {tap} from "rxjs/operators";
import {MatButton} from "@angular/material/button";
import {TopicCardComponent} from "@app/shared/components/topic-card/topic-card.component";
import {Id} from "@app/shared/models/id.model";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-topic',
  standalone: true,
  imports: [
    AsyncPipe,
    MatButton,
    TopicCardComponent
  ],
  templateUrl: './topic.component.html',
  styleUrl: './topic.component.scss'
})
export default class TopicComponent implements OnDestroy{
  private readonly _topicService: TopicService = inject(TopicService);
  protected topics$: Observable<Topics> = this._topicService.getTopics().pipe(tap(console.log));
  private _matSnackBar: MatSnackBar = inject(MatSnackBar);

  protected subscribeToTopic(topicId: Id, topicName: string): void {
    this._topicService.subscribeToTopic(topicId).subscribe({
      next: () => this._matSnackBar.open(`Vous êtes désormais abonné à ${topicName}`),
      error: () => this._matSnackBar.open('Une erreur est survenue, veuillez réessayer plus tard.')
    });
  }

  ngOnDestroy(): void {
    this._matSnackBar.dismiss();
  }
}
