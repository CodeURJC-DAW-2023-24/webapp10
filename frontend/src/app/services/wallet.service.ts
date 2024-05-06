import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class WalletService {

  private apiUrl: string = '/api/wallets';

  private card_number!: string;
  private expired_date!: string;
  private money!: number;
  private cvv!: string;

  constructor(private http: HttpClient, private authService: AuthService) {
    this.initializeWalletBackend();
  }

  private initializeWalletBackend(): void {
    let userId: number | undefined;

    if (this.authService.isLogged()) {
      userId = this.authService.currentUser()?.id;
    }

    this.http.get<any>(`${this.apiUrl}/${userId}`).subscribe(
      (walletData) => {
        this.card_number = walletData.card_number;
        this.expired_date = walletData.expired_date;
        this.money = walletData.money;
        this.cvv = walletData.cvv;
      },
      (error) => {
        console.error('Error al obtener los datos de la wallet del usuario: ', error);
      }
    )
  }

  getCardNumber(): string {
    return this.card_number;
  }

  getMoney(): number {
    return this.money;
  }

  getExpiredDate(): string {
    return this.expired_date;
  }

  getCvv(): string {
    return this.cvv;
  }
}
