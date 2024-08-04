import {Component} from '@angular/core';
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-error-page',
  standalone: true,
  imports: [RouterLink],
  template: `
      <div class="w-full h-full flex justify-center items-center">
          <p> Erreur 404 </p>
          <p> Il semblerait que la page que vous cherchez n'existe pas. </p>
          <div>
              <button routerLink="/article" color="primary"> Retour à l'accueil</button>
          </div>
      </div>
  `,
  styles: []
})
export class ErrorPageComponent {
}
