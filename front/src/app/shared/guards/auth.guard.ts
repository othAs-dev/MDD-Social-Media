import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import {switchMap, of, map} from 'rxjs';
import {AuthService} from "@app/auth/auth.service";

export const authGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  return authService.isAuthenticated().pipe(
    map(isAuthenticated => {
      if (!isAuthenticated) {
        router.navigate(['/auth/login']);
      }
      return isAuthenticated;
    })
  );
};
