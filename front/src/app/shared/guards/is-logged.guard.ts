import {CanActivateFn, Router} from "@angular/router";
import {inject} from "@angular/core";
import {AuthService} from "@app/auth/auth.service";

export const isLoggedGuard: CanActivateFn = (route, state) => {
  const authService: AuthService = inject(AuthService);
  if (authService.isAuthenticated()) {
    const router = inject(Router);
    router.navigate(['/blog']);
    return false;
  }
  return true;
};
