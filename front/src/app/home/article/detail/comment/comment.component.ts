import { Component, inject, Input } from '@angular/core';
import { CommentService } from './comment.service';
import {Observable, startWith, Subject, switchMap, take, tap} from 'rxjs';
import { Comments } from "@app/home/article/detail/comment/comment.model";
import { Id } from "@app/shared/models/id.model";
import { MatCard, MatCardContent, MatCardFooter } from "@angular/material/card";
import { AsyncPipe, DatePipe, NgForOf, NgIf } from "@angular/common";
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms";
import { MatButton, MatIconButton } from "@angular/material/button";
import { MatFormField } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { MatOption } from "@angular/material/autocomplete";
import { MatSelect } from "@angular/material/select";
import { MatIcon } from "@angular/material/icon";
import { MatSnackBar } from '@angular/material/snack-bar';
import {catchError} from "rxjs/operators";

@Component({
  selector: 'app-comment',
  standalone: true,
  imports: [
    MatCard,
    MatCardContent,
    MatCardFooter,
    AsyncPipe,
    FormsModule,
    MatButton,
    MatFormField,
    MatInput,
    MatOption,
    MatSelect,
    NgForOf,
    NgIf,
    ReactiveFormsModule,
    MatIcon,
    MatIconButton,
    DatePipe
  ],
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.scss']
})
export class CommentComponent {
  @Input({ required: true }) comments!: Comments;
  @Input({ required: true }) articleId!: Id;

  private readonly _commentService: CommentService = inject(CommentService);
  private _matSnackBar: MatSnackBar = inject(MatSnackBar);

  private readonly refresh$ = new Subject<void>();


  /**
   *
   * @description Observable of comments
   *
   * @returns Observable<Comments>
   *
   * */
  protected comments$: Observable<Comments> = this.refresh$.pipe(
    startWith(null),
    switchMap(() => this._commentService.getComments$(this.articleId)),
    catchError(() => {
      this._matSnackBar.open('Une erreur est survenue, veuillez réessayer plus tard.', 'Fermer');
      return [];
    })
  );

  /**
   *
   * @description Comment form group to add a comment to the article
   * */
  protected commentForm: FormGroup = new FormGroup({
    content: new FormControl('', [Validators.required]),
  });


  /**
   *
   * @param articleId
   * @param content
   * @returns void
   * @description Add a comment to the article
   *
   * @example
   * addComment(1, 'This is a comment');
   *
   * */
  protected addComment(articleId: Id, content: string): void {
    this._commentService.postComment$(articleId, content).pipe(
      take(1),
      tap(() => {
        this.refresh$.next();
        this.commentForm.reset();
        this._matSnackBar.open('Commentaire ajouté avec succès.', 'Fermer');
      }),
      catchError(() => {
        this._matSnackBar.open('Une erreur est survenue, veuillez réessayer plus tard.', 'Fermer');
        return [];
      })
    ).subscribe();
  }
}
