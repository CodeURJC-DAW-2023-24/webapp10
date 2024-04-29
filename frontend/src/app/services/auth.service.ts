import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {

private baseUrl = '/api/auth/';

constructor(private http: HttpClient) {}

login(loginRequest: any, accessToken: string, refreshToken: string): Observable<any> {
  return this.http.post<any>(`${this.baseUrl}/login`, loginRequest, { headers: { 'accessToken': accessToken, 'refreshToken': refreshToken } });
}

refreshToken(refreshToken: string): Observable<any> {
  return this.http.post<any>(`${this.baseUrl}/refresh`, null, { headers: { 'refreshToken': refreshToken } });
}

logout(): Observable<any> {
  return this.http.post<any>(`${this.baseUrl}/logout`, null);
}

}
