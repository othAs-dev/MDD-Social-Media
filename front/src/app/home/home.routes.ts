import { Routes } from '@angular/router';
import HomeComponent from './home.component';
import { authGuard } from '@app/config/guards/auth.guard';

export default [
  {
    path: '',
    component: HomeComponent,
    canActivate: [authGuard],
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'not-found',
      },
      {
        path: 'article',
        loadChildren: () => import('@app/home/article/article.routes'),
      },
      {
        path: 'user-profil',
        loadComponent: () => import('./user-profil/user-profil.component'),
      },
      {
        path: 'topic',
        loadComponent: () => import('@app/home/topic/topic.component'),
      },
      {
        path: 'not-found',
        loadComponent: () => import('@app/not-found/not-found.component'),
      },
    ],
  },
  {
    path: '**',
    redirectTo: '', // Global fallback redirecting to the home route
  },
] as Routes;
