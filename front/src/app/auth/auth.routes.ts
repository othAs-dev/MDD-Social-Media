import {Routes} from "@angular/router";

export default [
  {
    path: '',
    loadComponent: () => import('./auth.component'),
  },
  {
    path: 'login',
    loadComponent: () => import('./login/login.component'),
  },
  {
    path: 'register',
    loadComponent: () => import('./register/register.component'),
  },
  {
    path: '**',
    pathMatch: 'full',
    redirectTo: '',
  },
] as Routes;
