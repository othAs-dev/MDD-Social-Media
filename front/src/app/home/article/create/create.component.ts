import {Component, inject} from '@angular/core';
import {MatIcon} from "@angular/material/icon";
import {MatButton, MatIconButton} from "@angular/material/button";
import {RouterLink} from "@angular/router";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatError, MatFormField} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {AsyncPipe, NgForOf, NgIf} from "@angular/common";
import {Article} from "@app/home/article/article.model";
import {Observable, take} from "rxjs";
import {Topics} from "@app/home/topic/topic.model";
import {TopicService} from '@app/home/topic/topic.service';
import {CreateArticleService} from "@app/home/article/create/create-article.service";
import {MatOption, MatSelect} from "@angular/material/select";
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-create',
  standalone: true,
  imports: [
    MatIcon,
    MatIconButton,
    RouterLink,
    FormsModule,
    MatButton,
    MatError,
    MatFormField,
    MatInput,
    NgIf,
    ReactiveFormsModule,
    MatSelect,
    MatOption,
    AsyncPipe,
    NgForOf
  ],
  templateUrl: './create.component.html',
  styleUrl: './create.component.scss'
})
export default class CreateComponent {
  private readonly _createArticleService: CreateArticleService = inject(CreateArticleService);
  private readonly _topicService: TopicService = inject(TopicService);
  protected readonly topics$: Observable<Topics> = this._topicService.getTopics();
  private readonly _snackBar: MatSnackBar = inject(MatSnackBar);

  protected articleForm: FormGroup = new FormGroup({
    topicTitle: new FormControl('', [Validators.required]),
    title: new FormControl('', [Validators.required]),
    description: new FormControl('', [Validators.required]),
  });

  protected createArticle(article: Article): void {
    this._createArticleService.createArticle(article).pipe(take(1)).subscribe({
      next: () => {
        this.articleForm.reset();
        this._snackBar.open('Article ajouté avec succès', 'Fermer');
      },
      error: () => {
        this._snackBar.open('Une erreur est survenue lors de la création de l\'article', 'Fermer');
      }
    });
  }
}
