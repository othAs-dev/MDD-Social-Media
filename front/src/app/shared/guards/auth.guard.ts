import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { switchMap, of } from 'rxjs';
import {AuthService} from "@app/auth/auth.service";

export const authGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  return authService.getAccessToken().pipe(
    switchMap(token => {
      if (token) {
        return of(true);
      } else {
        router.navigate(['/auth']);
        return of(false);
      }
    })
  );
};
