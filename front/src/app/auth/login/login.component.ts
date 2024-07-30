import {Component} from "@angular/core";
import {LayoutComponent} from "../layout/layout.component";
import {FormControl, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {MatError, MatFormField} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button";
import {NgIf} from "@angular/common";


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
  protected loginForm: FormGroup = new FormGroup({
    email: new FormControl(''),
    password: new FormControl(''),
  });

  login() {
    if (this.loginForm.valid) {
      console.log(this.loginForm.value);
    }
  }
}
