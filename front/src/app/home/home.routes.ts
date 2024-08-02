import { Routes } from '@angular/router';
import HomeComponent from "./home.component";
import {authGuard} from "@app/config/guards/auth.guard";

export default [
  {
    path: '',
    component: HomeComponent,
    canActivate: [authGuard],
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'blog',
      },
      {
        path: 'blog',
        loadChildren: () => import('./blog/blog.routes'),
      },
      {
        path: 'user-profil',
        loadComponent: () => import('./user-profil/user-profil.component'),
      },
      {
        path: 'theme',
        loadComponent: () => import('./theme/theme.component'),
      },
    ],
  },
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'blog',
  },
] as Routes;
