import { Component } from '@angular/core';
import {RouterOutlet} from "@angular/router";
import {LayoutComponent} from "./layout/layout.component";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    RouterOutlet,
    LayoutComponent
  ],
  templateUrl: './home.component.html',
  styles: []
})
export default class HomeComponent {}
