import {Routes} from "@angular/router";
import {isLoggedGuard} from "@app/shared/guards/is-logged.guard";

export const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'auth',
        loadChildren: () => import('./auth/auth.routes'),
        canActivateChild: [isLoggedGuard],
      },
      {
        path: '',
        loadChildren: () => import('./home/home.routes'),
      },
      {
        path: '**',
        pathMatch: 'full',
        redirectTo: '',
      },
    ],
  },
] as Routes;
