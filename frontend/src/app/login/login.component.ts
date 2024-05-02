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
    console.log("ESTA AUTENTICADO: ", this.authService.isLogged());
    console.log("USUARIO Y CONTRASEÑA: ", this.authService.currentUser()?.username, this.authService.currentUser()?.password);
  }

  logOut() {
    this.authService.logOut();
  }

}
