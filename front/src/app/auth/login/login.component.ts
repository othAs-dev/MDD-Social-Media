import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatError, MatFormField } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatButton } from '@angular/material/button';
import { NgIf } from '@angular/common';
import { LayoutComponent } from '../layout/layout.component';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import {Login} from "@app/auth/login/models/login.model";
import {LoginService} from "@app/auth/login/login.service";
import {take} from "rxjs";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    LayoutComponent,
    ReactiveFormsModule,
    MatError,
    MatFormField,
    MatInput,
    MatButton,
    NgIf
  ],
  templateUrl: './login.component.html',
  styles: []
})
export default class LoginComponent {
  private readonly _router: Router = inject(Router);
  private readonly _snackBar: MatSnackBar = inject(MatSnackBar);
  private readonly _loginService: LoginService = inject(LoginService);

  protected loginForm: FormGroup = new FormGroup({
    userOrEmail: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required, Validators.minLength(8)])
  });

  protected login(credentials: Login): void {
    if (this.loginForm.valid) {
      this._loginService.login(credentials).pipe(take(1)).subscribe({
        next: () => {
          this._snackBar.open('Bonjour !');
          this._router.navigate(['/article']);
        },
        error: (error) => {
          this._snackBar.open('Authentification échoué, verifiez vos informations.');
          console.error('Login error:', error);
        }
      });
    }
  }
}
