import { Component, inject } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { UserProfilService } from './user-profil.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '@app/auth/auth.service';
import {Observable, Subject, switchMap, startWith, take} from 'rxjs';
import { UpdateUserProfil, UserProfil } from '@app/home/user-profil/user-profil.model';
import { AsyncPipe, NgIf } from '@angular/common';
import { catchError, tap } from 'rxjs/operators';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatError, MatFormField } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { passwordValidator } from '@app/auth/register/utils/passwordValidator';
import { TopicCardComponent } from '@app/shared/components/topic-card/topic-card.component';
import { Topics } from '@app/home/topic/topic.model';
import { Id } from '@app/shared/models/id.model';

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
  private readonly _snackBar: MatSnackBar = inject(MatSnackBar);
  private readonly _userProfilService: UserProfilService = inject(UserProfilService);

  /**
   * Subject to trigger refresh of user topics.
   */
  protected refresh$ = new Subject<void>();

  /**
   * Observable that emits the list of topics the user is subscribed to.
   * Refreshes the topics list whenever `refresh$` emits.
   */
  protected topics$: Observable<Topics> = this.refresh$.pipe(
    startWith(null),
    switchMap(() => this._userProfilService.getUserTopics()),
    catchError(() => {
      this._snackBar.open('Une erreur est survenue, veuillez réessayer plus tard.');
      return [];
    })
  );

  /**
   * Form group for user details including email, username, and password.
   */
  protected userDetailsForm: FormGroup = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required, passwordValidator()])
  });

  /**
   * Observable that emits the current user profile details.
   * Updates the form values with the user profile details when the observable emits.
   */
  protected readonly userDetails$: Observable<UserProfil> = this._userProfilService.get().pipe(
    tap(user => this.userDetailsForm.patchValue(user, { emitEvent: false }))
  );

  /**
   * Saves the updated user profile details.
   * Submits the updated profile data to the server and handles success or error response.
   * @param id The ID of the user whose profile is to be updated.
   * @param credentials The updated user profile data.
   */
  protected save(id: string, credentials: UserProfil): void {
    if (this.userDetailsForm.valid) {
      this._userProfilService.save(id, credentials).pipe(take(1)).subscribe({
        next: (response: UpdateUserProfil) => {
          this._snackBar.open('Informations enregistrées avec succès !', 'Fermer');

          const newToken = response.accessToken;
          if (newToken) {
            this._authService.updateToken(newToken);
          }
        },
        error: () => this._snackBar.open('Échec de l\'enregistrement, veuillez vérifier vos informations.', 'Fermer')
      });
    }
  }

  /**
   * Logs out the current user and navigates to the login page.
   */
  protected logout(): void {
    this._authService.logout();
    this._snackBar.open('Déconnexion réussie');
  }

  /**
   * Unsubscribes the user from a specific topic.
   * Updates the list of topics and displays a confirmation message upon successful unsubscription.
   * @param topicId The ID of the topic to unsubscribe from.
   * @param topicName The name of the topic for display purposes.
   */
  protected unsubscribeToTopic(topicId: Id, topicName: string): void {
    this._userProfilService.unsubscribeToTopic(topicId).pipe(take(1)).subscribe({
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
