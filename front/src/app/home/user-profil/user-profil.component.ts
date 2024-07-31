import { Component, inject } from '@angular/core';
import {MatButton} from "@angular/material/button";
import { UserProfilService } from './user-profil.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import {AuthService} from "@app/auth/auth.service";

@Component({
  selector: 'app-user-profil',
  standalone: true,
  imports: [
    MatButton
  ],
  templateUrl: './user-profil.component.html',
  styleUrl: './user-profil.component.scss'
})
export default class UserProfilComponent {
  private readonly _authService: AuthService = inject(AuthService);
  private readonly _snackBar: MatSnackBar = inject(MatSnackBar);
  protected logout(): void {
    this._authService.logout();
    this._snackBar.open('Déconnexion réussie');
  }
}
