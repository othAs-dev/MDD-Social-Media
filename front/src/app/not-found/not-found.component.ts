import {Component, inject} from '@angular/core';
import {Router, RouterLink} from "@angular/router";
import {MatButton} from "@angular/material/button";

@Component({
  selector: 'app-not-found',
  standalone: true,
  imports: [RouterLink, MatButton],
  template: `
      <section class="bg-white">
          <div class="py-8 px-4 mx-auto max-w-screen-xl laptop:py-16 lg:px-6">
              <div class="mx-auto max-w-screen-sm text-center">
                  <h1 class="mb-4 text-7xl tracking-tight font-extrabold laptop:text-9xl text-primary-600">404</h1>
                  <p class="mb-4 text-3xl tracking-tight font-bold text-gray-900 laptop:text-4xl">Quelque chose s'est mal passé</p>
                  <p class="mb-4 text-lg font-light text-gray-500">Désolé, nous n'arrivons pas à récupérer la ressource demandée. </p>
                  <button mat-button mat-raised-button color="primary" routerLink="/article">Retour à la page d'acceuil</button>
              </div>
          </div>
      </section>
  `,
  styles: []
})
export default class NotFoundComponent {
  private _router: Router = inject(Router);
  protected readonly currentUrl: string = this._router.url;
}
