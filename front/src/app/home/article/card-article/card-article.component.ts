import {Component, Input} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {
  MatCard,
  MatCardContent,
  MatCardFooter,
  MatCardHeader,
  MatCardSubtitle,
  MatCardTitle
} from "@angular/material/card";
import {DatePipe} from "@angular/common";
import {RouterLink} from "@angular/router";
import {Article} from "@app/home/article/article.model";

@Component({
  selector: 'app-card-article',
  standalone: true,
  imports: [
    MatButton,
    MatCard,
    MatCardContent,
    MatCardFooter,
    MatCardHeader,
    MatCardTitle,
    MatCardSubtitle,
    DatePipe,
    RouterLink
  ],
  templateUrl: './card-article.component.html',
  styleUrl: './card-article.component.scss'
})
export class CardArticleComponent {
  @Input({required: true}) article!: Article;
}
