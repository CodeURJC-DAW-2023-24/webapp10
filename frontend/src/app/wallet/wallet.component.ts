import { Component } from '@angular/core';

import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { CopyrightComponent } from '../copyright/copyright.component';

@Component({
  selector: 'app-wallet',
  templateUrl: './wallet.component.html',
  styleUrl: './wallet.component.css'
})
export class WalletComponent {

  card_number! : string;
  expired_date! : string;
  money! : number;
  cvv! : string;

  addMoney(form: any): void {
    if (form.valid) {

    }
  }
}
