import { Component } from '@angular/core';
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-article',
  standalone: true,
  imports: [
    NgForOf
  ],
  templateUrl: './article.component.html',
  styleUrl: './article.component.scss'
})
export class ArticleComponent {
  articles = [
    { date: '2024-07-29', auteur: 'Author 1', content: 'Lorem ipsum is simply dummy text of the printing and typesetting industry.' },
    { date: '2024-07-28', auteur: 'Author 2', content: 'Lorem ipsum has been the industry\'s standard dummy text ever since the 1500s.' },
    // Add more articles as needed
  ];
}
