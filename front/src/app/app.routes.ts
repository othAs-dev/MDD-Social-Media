import {Routes} from "@angular/router";

export const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'auth',
        loadChildren: () => import('./auth/auth.routes'),
      },
      {
        path: '**',
        pathMatch: 'full',
        redirectTo: 'auth',
      },
    ],
  },
] as Routes;
