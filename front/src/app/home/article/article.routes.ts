import {Routes} from "@angular/router";
import {ArticleComponent} from "./article.component";

export default [
  {
    path: '',
    component: ArticleComponent
    //loadComponent: import('./article.component').then(m => m.ArticleComponent),
  },
  {
    path: '**',
    pathMatch: "full",
    redirectTo: ''
  }
] as Routes
