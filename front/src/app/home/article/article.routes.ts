import { Routes } from '@angular/router';
import { ArticleResolver } from '@app/home/article/article.resolver';

export default [
  {
    path: '',
    loadComponent: () => import('@app/home/article/article.component'),
  },
  {
    path: 'create',
    loadComponent: () => import('@app/home/article/create/create.component'),
  },
  {
    path: ':articleId',
    loadComponent:  () => import('@app/home/article/detail/detail.component'),
    resolve: { article: ArticleResolver },
  },
  {
    path: '**',
    pathMatch: 'full',
    redirectTo: '',
  },
] as Routes;
