import { Component } from '@angular/core';
import {LayoutComponent} from "../layout/layout.component";
import {MatButton} from "@angular/material/button";
import {MatFormField} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {FormControl, FormGroup, ReactiveFormsModule} from "@angular/forms";

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
  protected registerForm: FormGroup = new FormGroup({
    username: new FormControl(''),
    email: new FormControl(''),
    password: new FormControl(''),
  });

  register() {
    if (this.registerForm.valid) {
      console.log(this.registerForm.value);
    }
  }
}
