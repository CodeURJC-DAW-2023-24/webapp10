import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { User } from '../models/user.model';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  user : User = {} as User;
 
  constructor(private authService: AuthService) {}
  

  registerUser(){
    

    this.authService.register(this.user)
    
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
