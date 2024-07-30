import { Component } from '@angular/core';
import {MatProgressBar} from "@angular/material/progress-bar";
import {MatButton} from "@angular/material/button";
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [
    MatProgressBar,
    MatButton,
    RouterLink
  ],
  templateUrl: './auth.component.html',
  styles: []
})
export default class AuthComponent {

}
