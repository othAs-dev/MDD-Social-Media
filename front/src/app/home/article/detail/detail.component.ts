import { Component, Input } from '@angular/core';
import {Article} from "@app/home/article/article.model";
import {MatIcon} from "@angular/material/icon";
import {MatIconButton} from "@angular/material/button";
import {RouterLink} from "@angular/router";
import {DatePipe} from "@angular/common";
import {CommentComponent} from "@app/home/article/detail/comment/comment.component";

@Component({
  selector: 'app-detail',
  standalone: true,
  imports: [
    MatIcon,
    MatIconButton,
    RouterLink,
    DatePipe,
    CommentComponent
  ],
  templateUrl: './detail.component.html',
  styles: []
})
export default class DetailComponent {
  @Input({required: true}) article!: Article;
}
