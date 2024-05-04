import { Component } from '@angular/core';

import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { CopyrightComponent } from '../copyright/copyright.component';
import {AuthService} from "../services/auth.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  public authServicer! : AuthService;

  constructor(private authService: AuthService) {
    this.authServicer = authService;
  }
}
