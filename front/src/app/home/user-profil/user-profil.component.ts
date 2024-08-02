import { Component, inject } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { UserProfilService } from './user-profil.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '@app/auth/auth.service';
import { Observable, Subject, startWith, switchMap } from 'rxjs';
import { UserProfil } from '@app/home/user-profil/user-profil.model';
import { AsyncPipe, NgIf } from '@angular/common';
import { catchError, tap } from 'rxjs/operators';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatError, MatFormField } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { passwordValidator } from '@app/auth/register/utils/passwordValidator';
import { TopicCardComponent } from '@app/shared/components/topic-card/topic-card.component';
import { TopicService } from '@app/home/topic/topic.service';
import { Topics } from '@app/home/topic/topic.model';
import { Id } from '@app/shared/models/id.model';
import { AccessToken } from '@app/auth/models/accessToken.model';

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
    ReactiveFormsModule,
    TopicCardComponent
  ],
  templateUrl: './user-profil.component.html',
  styleUrls: ['./user-profil.component.scss']
})
export default class UserProfilComponent {
  private readonly _authService: AuthService = inject(AuthService);
  protected refresh$ = new Subject<void>();
  private readonly _snackBar: MatSnackBar = inject(MatSnackBar);
  private readonly _userProfilService: UserProfilService = inject(UserProfilService);
  protected topics$: Observable<Topics> = this.refresh$.pipe(
    startWith(null),
    switchMap(() => this._userProfilService.getUserTopics()),
    catchError(() => {
      this._snackBar.open('Une erreur est survenue, veuillez réessayer plus tard.');
      return [];
    })
  );

  protected userDetailsForm: FormGroup = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required, passwordValidator()])
  });

  protected readonly userDetails$: Observable<UserProfil> = this._userProfilService.get().pipe(
    tap(user => this.userDetailsForm.patchValue(user, { emitEvent: false }))
  );

  protected save(id: string, credentials: UserProfil): void {
    if (this.userDetailsForm.valid) {
      this._userProfilService.save(id, credentials).subscribe({
        next: (response: AccessToken) => {
          this._snackBar.open('Informations enregistrées avec succès !', 'Fermer');

          const newToken = response.accessToken;
          if (newToken) {
            this._authService.updateToken(newToken); // newToken est maintenant de type string
          }
        },
        error: () => this._snackBar.open('Echec de l\'enregistrement, veuillez vérifier vos informations.', 'Fermer')
      });
    }
  }

  protected logout(): void {
    this._authService.logout();
    this._snackBar.open('Déconnexion réussie');
  }

  protected unsubscribeToTopic(topicId: Id, topicName: string): void {
    this._userProfilService.unsubscribeToTopic(topicId).subscribe({
      next: () => {
        this.refresh$.next();
        this._snackBar.open(`Vous êtes désormais désabonné de ${topicName}`, 'Fermer');
      },
      error: () => {
        this._snackBar.open('Une erreur est survenue, veuillez réessayer plus tard.', 'Fermer');
      }
    });
  }
}
