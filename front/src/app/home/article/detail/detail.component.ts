import { Component, Input } from '@angular/core';
import {Article} from "@app/home/article/article.model";
import {MatIcon} from "@angular/material/icon";
import {MatIconButton} from "@angular/material/button";
import {RouterLink} from "@angular/router";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-detail',
  standalone: true,
  imports: [
    MatIcon,
    MatIconButton,
    RouterLink,
    DatePipe
  ],
  templateUrl: './detail.component.html',
  styleUrl: './detail.component.scss'
})
export default class DetailComponent {
  @Input({required: true}) article!: Article;
  ngOnInit() {
    console.log(this.article);
  }
}
