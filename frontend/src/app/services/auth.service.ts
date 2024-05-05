import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user.model';
import { Observable, ReplaySubject, catchError, throwError } from 'rxjs';
import { Wallet } from '../models/wallet.model';
import { RegistrationDataDTO } from '../models/registrationDataDTO';

const BASE_URL = '/api/auth';
const BASE_URL_USERS = '/api/users/';

@Injectable({ providedIn: 'root' })
export class AuthService {

  logged: boolean = false;
  user: User | undefined;

  constructor(private http: HttpClient) {
      this.reqIsLogged();
  }

  reqIsLogged() {
    this.http.get('/api/users/me', { withCredentials: true }).subscribe(
          response => {
            if (response) {
              console.log("Tenia contenido");
              this.user = response as User;
              this.logged = true;
            }else {
              console.log("No tenía contenido");
              this.user = undefined;
              this.logged = false;
            }
          },
          error => {
              if (error.status != 404) {
                  console.error('Error when asking if logged: ' + JSON.stringify(error));
              }
          }
    );
  }

  logIn(user: string, pass: string) {
      this.http.post(BASE_URL + "/login", { username: user, password: pass }, { withCredentials: true })
          .subscribe(
              (response) =>
                this.reqIsLogged(),


              (error) => alert("Wrong credentials")
          );
  }

  logOut() {

      return this.http.post(BASE_URL + '/logout', { withCredentials: true })
          .subscribe((resp: any) => {
              console.log("LOGOUT: Successfully");
              this.logged = false;
              this.user = undefined;
          });

  }

  isLogged() {
      return this.logged;
  }
  isAdmin(): boolean {

    return this.user && this.user.roles?.includes('ADMIN') ? true : false;
  }

  currentUser() {
      return this.user;
  }

  register(user: User, wallet: Wallet) {

    const registrationData: RegistrationDataDTO = {
      user: user,
      wallet: wallet
    }

    if (user.roles) {
        user.roles.push('USER');
    } else {
        user.roles = ['USER'];
    }

    console.log("EL USUARIO ES: ", user.username);
    console.log("LA WALLET TIENE: ", user.wallet?.money);


    console.log("DTO USER: ", registrationData.user.username);
    console.log("LA WALLET TIENE: ", registrationData.wallet.money);

      return this.http.post(BASE_URL_USERS, user).pipe(
          catchError(error => {
              console.error('Error registering user: ' + JSON.stringify(error));
              return throwError('Error registering user');
          })
      );
    }
    setRole(user: User){
        user.roles.push("USER");
    }

    getId(): number {
        return this.user?.id ?? 1;
    }

    getUserDetails() {
        return this.user;
    }

    getUserImage(id: number) {

        return this.http.get( `${BASE_URL_USERS}image/${id}`, { responseType: 'blob' });
      }

    updateUserImage(id: number, image: File) {
        console.log("HASTA ANTES DEL PUT SI");
        const formData = new FormData();
        formData.append('imageFile', image);
        return this.http.put(`${BASE_URL_USERS}image/${id}`, formData);
    }
}
