import { Component, inject } from '@angular/core';
import {MatButton} from "@angular/material/button";
import { UserProfilService } from './user-profil.service';

@Component({
  selector: 'app-user-profil',
  standalone: true,
  imports: [
    MatButton
  ],
  templateUrl: './user-profil.component.html',
  styleUrl: './user-profil.component.scss'
})
export default class UserProfilComponent {
  private readonly __userProfilService: UserProfilService = inject(UserProfilService);

  protected logout(): void {
    this.__userProfilService.logout();
  }
}
