import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { WalletService } from '../services/wallet.service';

@Component({
  selector: 'app-wallet',
  templateUrl: './wallet.component.html',
  styleUrl: './wallet.component.css'
})
export class WalletComponent implements OnInit {
  card_number: string | undefined;
  expired_date: string | undefined;
  money: number | undefined;
  cvv: string | undefined;
  user: any;

  constructor(private authService: AuthService, private router: Router, private walletService: WalletService) {}

  ngOnInit(): void {
    if (this.authService.isLogged()) {
      this.card_number = this.walletService.getCardNumber();
      this.expired_date = this.walletService.getExpiredDate();
      this.money = this.walletService.getMoney();
      this.cvv = this.walletService.getCvv();
      this.user = this.authService.getUserDetails();
    }
  }

  addMoney(input_cvv: string, amount: number): void {
    console.log('CVV:', input_cvv);
    console.log('Cantidad a añadir:', amount);

    if (input_cvv == this.cvv) {
      // @ts-ignore
      this.money += amount;
      console.log('Se ha completado la operacion');
    }
  }
}
