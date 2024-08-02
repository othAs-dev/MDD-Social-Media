import {Component, inject} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {UserProfilService} from './user-profil.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {AuthService} from "@app/auth/auth.service";
import {Observable} from "rxjs";
import {UserProfil} from "@app/home/user-profil/user-profil.model";
import {AsyncPipe, NgIf} from "@angular/common";
import {tap} from "rxjs/operators";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatError, MatFormField} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {passwordValidator} from "@app/auth/register/utils/passwordValidator";

@Component({
  selector: 'app-user-profil',
  standalone: true,
  imports: [
    MatButton,
    AsyncPipe,
    NgIf,
    FormsModule,
    MatError,
    MatFormField,
    MatInput,
    ReactiveFormsModule
  ],
  templateUrl: './user-profil.component.html',
  styleUrl: './user-profil.component.scss'
})
export default class UserProfilComponent {
  private readonly _authService: AuthService = inject(AuthService);
  private readonly _snackBar: MatSnackBar = inject(MatSnackBar);
  private readonly _userProfilService: UserProfilService = inject(UserProfilService);
  protected userDetailsForm: FormGroup = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [ Validators.required, passwordValidator() ])
  });
  protected readonly userDetails$: Observable<UserProfil> = this._userProfilService.get().pipe(tap(user =>
    this.userDetailsForm.patchValue(user, { emitEvent: false })
  ));


  protected save(id: string,credentials: UserProfil): void {
    if (this.userDetailsForm.valid) {
      this._userProfilService.save(id, credentials).subscribe({
        next: () => {
          this._snackBar.open('Informations enregistrées avec succès !')
        },
        error: () => {
          this._snackBar.open('Echec de l\'enregistrement, veuillez vérifier vos informations.');
        }
      });
    }
  }

  protected logout(): void {
    this._authService.logout();
    this._snackBar.open('Déconnexion réussie');
  }

}
