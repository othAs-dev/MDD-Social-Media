import { Component } from '@angular/core';
import {MatProgressBar} from "@angular/material/progress-bar";

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [
    MatProgressBar
  ],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.scss'
})
export default class AuthComponent {

}
