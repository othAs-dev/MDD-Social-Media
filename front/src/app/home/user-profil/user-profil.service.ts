import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root',
})
export class UserProfilService {

  public logout(): void {
    sessionStorage.removeItem('token');
    localStorage.removeItem('user_details');
    window.location.href = '/marketplace/home';
  }
}
