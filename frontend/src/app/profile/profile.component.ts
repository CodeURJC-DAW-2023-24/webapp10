import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {
  

  user: any;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit() {
    if (this.authService.isLogged()) {
      this.user = this.authService.getUserDetails(); 
   
    }
   
  }

  logOut() {
    this.authService.logOut();
    this.router.navigate(['/home']);
  }

}