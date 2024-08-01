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
  @Input({required: true}) id!: string;
  @Input({required: true}) title!: string;
  @Input({required: true}) description!: string;
  @Input({required: true}) author!: string;
  @Input({required: true}) date!: string;
  @Input({required: true}) topicTitle!: string;

}
