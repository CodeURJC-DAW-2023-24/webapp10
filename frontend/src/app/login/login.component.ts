import { AuthService } from './../services/auth.service';
import { Component } from '@angular/core';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  user: string = '';
  pass: string = '';

  constructor(public authService: AuthService) { }

  logIn(event: any) {
    event.preventDefault();
    this.authService.login({ username: this.user, password: this.pass }, 'access_token', 'refresh_token')
      .subscribe(
        response => {
          // Manejar la respuesta del servicio, como almacenar tokens en el almacenamiento local o redirigir a otra página.
          console.log('Inicio de sesión exitoso:', response);
        },
        error => {
          // Manejar errores, como mostrar un mensaje de error al usuario.
          console.error('Error al iniciar sesión:', error);
        }
      );
  }

  logOut() {
    this.authService.logout()
      .subscribe(
        response => {
          // Manejar la respuesta del servicio, como limpiar los tokens del almacenamiento local o redirigir a otra página.
          console.log('Cierre de sesión exitoso:', response);
        },
        error => {
          // Manejar errores, como mostrar un mensaje de error al usuario.
          console.error('Error al cerrar sesión:', error);
        }
      );
  }
}
