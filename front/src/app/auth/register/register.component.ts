import {Component, inject} from '@angular/core';
import {LayoutComponent} from "../layout/layout.component";
import {MatButton} from "@angular/material/button";
import {MatFormField} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {Register} from "./models/register.model";
import {RegisterService} from "./register.service";
import {Router} from "@angular/router";
import {AccessToken} from "@app/auth/models/accessToken.model";

@Component({
  selector: 'app-register',
  standalone: true,
    imports: [
        LayoutComponent,
        MatButton,
        MatFormField,
        MatInput,
        ReactiveFormsModule
    ],
  templateUrl: './register.component.html',
  styles: []})
export default class RegisterComponent {
  private readonly _registerService: RegisterService = inject(RegisterService)
  private readonly _router: Router = inject(Router);

  protected registerForm: FormGroup = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    name: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
  });

  protected register(credentials: Register): void {
    if (this.registerForm.valid) {
      console.log(credentials);
      this._registerService.register(credentials).subscribe((res: AccessToken) => {
        sessionStorage.setItem('token', res.token);
        this._router.navigate(['/blog']);
      });
    }
  }
}
