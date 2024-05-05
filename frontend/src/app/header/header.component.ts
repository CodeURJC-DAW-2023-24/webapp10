import { Component, HostListener, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['../../styles.css', 'header.component.css']
})
export class HeaderComponent implements OnInit{
  scrolled: boolean = false;
  isAdmin : boolean = false;
  constructor(public authService: AuthService) { }



  ngOnInit() {
    this.isAdmin = this.authService.isLogged() && this.authService.isAdmin();
    console.log("Es admin" + this.authService.isAdmin());
    window.scrollTo(0, 0);
  }



  @HostListener('window:scroll', [])
  onWindowScroll() {
    if (window.scrollY > 0) {
      this.scrolled = true;
    } else {
      this.scrolled = false;
    }
  }

}
