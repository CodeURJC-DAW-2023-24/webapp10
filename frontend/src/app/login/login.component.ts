import { AuthService } from '../services/auth.service';
import { Component } from '@angular/core';
import { Router } from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  constructor(public authService: AuthService, private router: Router) { }

  logIn(event: any, user: string, pass: string) {

    event.preventDefault();

    this.authService.logIn(user, pass);

    console.log("ESTA AUTENTICADO: ", this.authService.isLogged());
    console.log("USUARIO Y CONTRASEÑA: ", this.authService.currentUser()?.username, this.authService.currentUser()?.password);
    console.log("ES ADMIN: ", this.authService.isAdmin());
    this.router.navigate(['/home']);
  }

  logOut() {
    this.authService.logOut();
  }

}
