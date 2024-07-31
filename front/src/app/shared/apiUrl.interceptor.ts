import { HttpEvent, HttpHandlerFn, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Environment} from "@app/shared/models/environnement.model";

export function apiUrlInterceptor(
  req: HttpRequest<unknown>,
  next: HttpHandlerFn,
  environment: Environment
): Observable<HttpEvent<unknown>> {
  if (req.url.startsWith('api/')) {
    return next(
      req.clone({
        url: `${environment.apiUrl}/${req.url}`,
      })
    );
  }
  return next(req);
}
