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

  card_number : string = '';
  expired_date : string = '';
  money : number = 0;
  cvv : string = '';

  constructor(private walletService: WalletService) { }

  addMoney(input_cvv: string, amount: number): void {
    console.log('CVV:', input_cvv);
    console.log('Cantidad a añadir:', amount);
  }
}
