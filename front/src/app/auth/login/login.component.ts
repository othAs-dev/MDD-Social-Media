import {Component, inject} from "@angular/core";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatError, MatFormField} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button";
import {NgIf} from "@angular/common";
import {LayoutComponent} from "../layout/layout.component";
import {LoginService} from "./login.service";
import {Login} from "./models/login.model";
import {AccessToken} from "@app/auth/models/accessToken.model";
import { Router } from "@angular/router";


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
  private readonly _loginService: LoginService = inject(LoginService)
  private readonly _router: Router = inject(Router);

  protected loginForm: FormGroup = new FormGroup({
    userOrEmail: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
  });

  protected login(credentials: Login): void {
    if (this.loginForm.valid) {
     this._loginService.login(credentials).subscribe((res: AccessToken) => {
       sessionStorage.setItem('token', res.token);
       this._router.navigate(['/blog']);
     }, (error) => {console.log(error)}
       );
    }
  }
}
