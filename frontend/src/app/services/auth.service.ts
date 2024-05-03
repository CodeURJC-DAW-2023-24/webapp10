import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user.model';
import { Observable, catchError, throwError } from 'rxjs';

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
              this.user = response as User;
              this.logged = true;
            }else {
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
              (response) => this.reqIsLogged(),
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

  isAdmin() {
      return this.user && (this.user?.roles?.includes('ADMIN') ?? false);
  }

  currentUser() {
      return this.user;
  }

  register(user: User) {
        return this.http.post(BASE_URL_USERS, user).pipe(
            catchError(error => {
                console.error('Error registering user: ' + JSON.stringify(error));
                return throwError('Error registering user');
            })
        );
    }

    getUserDetails (){
        return this.user;


    }
  
    getUserImage(id: number) {
    
        return this.http.get( `${BASE_URL_USERS}image/${id}`, { responseType: 'blob' });
      }
}
