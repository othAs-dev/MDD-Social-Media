import { Component, inject } from "@angular/core";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { MatError, MatFormField } from "@angular/material/form-field";
import { MatInput } from "@angular/material/input";
import { MatButton } from "@angular/material/button";
import { NgIf } from "@angular/common";
import { LayoutComponent } from "../layout/layout.component";
import { LoginService } from "./login.service";
import { Login } from "./models/login.model";
import { Router } from "@angular/router";
import { MatSnackBar } from "@angular/material/snack-bar";

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
  private readonly _loginService: LoginService = inject(LoginService);
  private readonly _router: Router = inject(Router);
  private readonly _snackBar: MatSnackBar = inject(MatSnackBar);

  protected loginForm: FormGroup = new FormGroup({
    userOrEmail: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required, Validators.minLength(8)])
  });

  protected login(credentials: Login): void {
    console.log(credentials);
    if (this.loginForm.valid) {
      this._loginService.login(credentials).subscribe({
        next: () => this._router.navigate(['/blog']),
        error: () => this._snackBar.open('Authentification échouée')
      });
    }
  }
}
