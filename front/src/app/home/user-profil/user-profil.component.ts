import { Component, inject } from '@angular/core';
import {MatButton} from "@angular/material/button";
import { UserProfilService } from './user-profil.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import {AuthService} from "@app/auth/auth.service";
import {Observable} from "rxjs";
import {UserProfil} from "@app/home/user-profil/user-profil.model";
import {AsyncPipe, NgIf} from "@angular/common";
import {tap} from "rxjs/operators";

@Component({
  selector: 'app-user-profil',
  standalone: true,
  imports: [
    MatButton,
    AsyncPipe,
    NgIf
  ],
  templateUrl: './user-profil.component.html',
  styleUrl: './user-profil.component.scss'
})
export default class UserProfilComponent {
  private readonly _authService: AuthService = inject(AuthService);
  private readonly _snackBar: MatSnackBar = inject(MatSnackBar);
  private readonly _userProfilService: UserProfilService = inject(UserProfilService);
  protected readonly userDetails$: Observable<UserProfil> = this._userProfilService.getUserProfil().pipe(tap(console.log));
  protected logout(): void {
    this._authService.logout();
    this._snackBar.open('Déconnexion réussie');
  }

}
