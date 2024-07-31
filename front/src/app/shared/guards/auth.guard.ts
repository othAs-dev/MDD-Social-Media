import {CanActivateFn, Router} from "@angular/router";
import {inject} from "@angular/core";

export const authGuard: CanActivateFn = (route, state) => {
  const token = sessionStorage.getItem('token');
  if (!token) {
    const router = inject(Router);
    router.navigate(['/auth/login']);
    return false;
  }
  return true;
};
