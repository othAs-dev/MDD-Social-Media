import { Component } from '@angular/core';
import {AsyncPipe, NgClass} from "@angular/common";
import {MatIcon} from "@angular/material/icon";
import {RouterLink, RouterLinkActive} from "@angular/router";
import {BehaviorSubject} from "rxjs";
import {MatIconButton} from "@angular/material/button";

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [
    AsyncPipe,
    MatIcon,
    RouterLink,
    RouterLinkActive,
    NgClass,
    MatIconButton
  ],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.scss'
})
export class LayoutComponent {

 protected isSidebarOpen$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
}
