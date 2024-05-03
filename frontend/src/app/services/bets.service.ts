import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Bet } from "../models/bets.model";
import { AuthService } from "./auth.service";

@Injectable({providedIn: 'root'})

export class BetsService {
  private baseUrl = "/api/bets/";
  bet!: Bet[];

  constructor(private http: HttpClient, private authService: AuthService) {
    this.initializeBetsBackend();
  }

  private initializeBetsBackend(): void {
    let userId: number | undefined;

    if (this.authService.isLogged()) {
      userId = this.authService.currentUser()?.id;
    }

    this.http.get<any>(`${this.baseUrl}/user/${userId}`).subscribe(
      (betsData) => {
        this.bet = betsData;
      },
      (error) => {
        console.error('Error al obtener las apuestas del usuario: ', error);
      }
    )
  }

  createBet(bet: Bet, eventId: Number): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}event/${eventId}`, bet);
  }

}
