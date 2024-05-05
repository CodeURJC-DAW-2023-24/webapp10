import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { User } from '../models/user.model';
import { Wallet } from '../models/wallet.model';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['../../styles.css', './register.component.css']
})
export class RegisterComponent {

  user : User = {} as User;
  wallet : Wallet;

  constructor(private authService: AuthService) {
    this.wallet = {
      card_number: '',
      expired_date: '',
      cvv: '',
      owner: '',
      money: 100
    };
  }


  registerUser(){

    this.wallet.owner = this.user.name;
    this.authService.register(this.user, this.wallet)

    .subscribe(
      user => {


        console.log(user);
      },
      error => {
        console.log(error);
      }
    );
  }

}
