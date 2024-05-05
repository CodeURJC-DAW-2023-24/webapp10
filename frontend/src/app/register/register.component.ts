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

  user : User;
  wallet : Wallet;

  constructor(private authService: AuthService) {
    this.user = new User();
    this.wallet = new Wallet();
  }

  registerUser(){

    this.wallet.owner = this.user.name;
    this.user.setWallet(this.wallet);
    this.wallet.setUser(this.user);

    this.authService.register(this.user, this.wallet).subscribe(
      user => {
        console.log(user);
      },
      error => {
        console.log(error);
      }
    );
  }

}
