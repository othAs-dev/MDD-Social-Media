import { Routes } from '@angular/router';
import HomeComponent from "./home.component";

export default [
  {
    path: '',
    component: HomeComponent,
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
    ],
  },
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'blog',
  },
] as Routes;
