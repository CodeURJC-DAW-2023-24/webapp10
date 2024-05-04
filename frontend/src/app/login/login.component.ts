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
    if (this.authService.isLogged()) {
      this.router.navigate(['/home']);
      window.scrollTo(0, 0);
    }
    console.log("ESTA AUTENTICADO: ", this.authService.isLogged());
    console.log("USUARIO Y CONTRASEÑA: ", this.authService.currentUser()?.username, this.authService.currentUser()?.password);
  }

  logOut() {
    this.authService.logOut();
  }

}
