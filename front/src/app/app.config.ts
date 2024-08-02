import {ApplicationConfig, importProvidersFrom, LOCALE_ID} from '@angular/core';
import {provideRouter, withComponentInputBinding} from '@angular/router';

import {routes} from './app.routes';
import {provideAnimations} from "@angular/platform-browser/animations";
import {provideHttpClient, withFetch, withInterceptors} from "@angular/common/http";
import {MAT_FORM_FIELD_DEFAULT_OPTIONS} from "@angular/material/form-field";
import {matsnackbarConfig} from "../../snackbar.config";
import {MAT_SNACK_BAR_DEFAULT_OPTIONS} from "@angular/material/snack-bar";
import {apiUrlInterceptor} from "@app/config/interceptors/apiUrl.interceptor";
import {environment} from "../environments/environment.local";
import {authInterceptor} from "@app/config/interceptors/auth.interceptor";

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(),
    provideRouter(routes, withComponentInputBinding()),
    provideAnimations(),
    importProvidersFrom(),
    provideHttpClient(
      withFetch(),
      withInterceptors([authInterceptor, (req, next) => apiUrlInterceptor(req, next, environment)])
    ),
    { provide: LOCALE_ID, useValue: "fr-FR" },
    { provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: { appearance: 'outline' } },
    { provide: MAT_SNACK_BAR_DEFAULT_OPTIONS, useValue: matsnackbarConfig, }
  ],
};
