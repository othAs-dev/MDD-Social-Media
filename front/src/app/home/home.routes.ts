import { Routes } from '@angular/router';
import HomeComponent from './home.component';
import { authGuard } from '@app/config/guards/auth.guard';

export default [
  {
    path: '',
    component: HomeComponent,
    canActivate: [authGuard], // Authentication guard for the home route
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'article', // Redirects to 'articles' by default
      },
      {
        path: 'article',
        loadChildren: () => import('@app/home/article/article.routes'), // Lazy loading articles module
      },
      {
        path: 'user-profil',
        loadComponent: () => import('./user-profil/user-profil.component'),
      },
      {
        path: 'topic',
        loadComponent: () => import('@app/home/topic/topic.component'),
      },
    ],
  },
  {
    path: '**',
    redirectTo: '', // Global fallback redirecting to the home route
  },
] as Routes;
