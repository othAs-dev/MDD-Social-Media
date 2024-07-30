import {Component, Input} from '@angular/core';
import {RouterLink} from "@angular/router";
import {MatIconModule} from '@angular/material/icon'
import {MatButton, MatFabButton, MatIconButton} from "@angular/material/button";


@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [
    RouterLink,
    MatIconModule,
    MatFabButton,
    MatButton,
    MatIconButton,
  ],
  templateUrl: './layout.component.html',
  styles: []
})
export class LayoutComponent {
  @Input({required: true}) title!: string;
}
