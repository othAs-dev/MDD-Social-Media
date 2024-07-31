import {Routes} from "@angular/router";
import { isLoggedGuard } from "@app/shared/guards/is-logged.guard";

export default [
  {
    path: '',
    loadComponent: () => import('./auth.component'),
    canActivate: [isLoggedGuard],
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
