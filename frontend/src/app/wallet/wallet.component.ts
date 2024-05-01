import { Component } from '@angular/core';

import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { CopyrightComponent } from '../copyright/copyright.component';
import { WalletService } from '../services/wallet.service';

@Component({
  selector: 'app-wallet',
  templateUrl: './wallet.component.html',
  styleUrl: './wallet.component.css'
})
export class WalletComponent {

  card_number : string;
  expired_date : string;
  money : number;
  cvv : string;

  constructor(private walletService: WalletService) {
    this.card_number = walletService.getCardNumber();
    this.expired_date = walletService.getExpiredDate();
    this.money = walletService.getMoney();
    this.cvv = walletService.getCvv();
  }

  addMoney(input_cvv: string, amount: number): void {
    console.log('CVV:', input_cvv);
    console.log('Cantidad a añadir:', amount);

    if (input_cvv == this.cvv) {
      this.money += amount;
      console.log('Se ha completado la operacion');
    }
  }
}
