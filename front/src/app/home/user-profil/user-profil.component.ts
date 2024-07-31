import { Component, inject } from '@angular/core';
import {MatButton} from "@angular/material/button";
import { UserProfilService } from './user-profil.service';
import { MatSnackBar } from '@angular/material/snack-bar';

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
  private readonly _userProfilService: UserProfilService = inject(UserProfilService);
  private readonly _snackBar: MatSnackBar = inject(MatSnackBar);
  protected logout(): void {
    this._userProfilService.logout();
    this._snackBar.open('Déconnexion réussie');
  }
}
