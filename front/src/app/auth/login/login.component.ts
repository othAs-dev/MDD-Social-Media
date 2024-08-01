import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatError, MatFormField } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatButton } from '@angular/material/button';
import { NgIf } from '@angular/common';
import { LayoutComponent } from '../layout/layout.component';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '@app/auth/auth.service';
import {Login} from "@app/auth/login/models/login.model";

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
  private readonly authService: AuthService = inject(AuthService);

  protected loginForm: FormGroup = new FormGroup({
    userOrEmail: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required, Validators.minLength(8)])
  });

  protected login(credentials: Login): void {
    if (this.loginForm.valid) {
      this.authService.login(credentials).subscribe({
        next: () => {
          this._snackBar.open('Login successful!', 'Close', { duration: 3000 });
          this._router.navigate(['/']); // Navigate to home or dashboard after login
        },
        error: (error) => {
          this._snackBar.open('Login failed. Please check your credentials.', 'Close', { duration: 3000 });
          console.error('Login error:', error);
        }
      });
    }
  }
}
