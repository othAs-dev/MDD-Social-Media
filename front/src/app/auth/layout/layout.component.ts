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
  template: `
      <div class="flex flex-col gap-5 h-full">
          <div class="hidden laptop:block">
              <div class="flex w-full justify-start">
                  <img src="assets/logo.png" alt="logo" class="w-40"/>
              </div>
              <div class="w-full">
                  <hr class="border-black"/>
              </div>
          </div>
          <div class="w-full p-3">
              <button mat-icon-button aria-label="icon button with left arrow" routerLink="../auth">
                  <mat-icon>keyboard_backspace</mat-icon>
              </button>
          </div>
          <div class="flex w-full justify-center laptop:hidden">
              <img src="assets/logo.png" alt="logo" class="w-60"/>
          </div>
          <div class="flex flex-col items-center w-full gap-5">
              <h1 class="text-2xl">{{ title }}</h1>
              <ng-content/>
          </div>
      </div>
  `,
  styles: []
})
export class LayoutComponent {
  @Input({required: true}) title!: string;
}
