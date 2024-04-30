import { AuthService } from './../services/auth.service';
import { Component } from '@angular/core';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  constructor(public authService: AuthService) { }

  logIn(event: any, user: string, pass: string) {

    event.preventDefault();

    this.authService.logIn(user, pass);
  }

  logOut() {
    this.authService.logOut();
  }

}
