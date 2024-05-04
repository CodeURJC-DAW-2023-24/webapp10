import { Component, HostListener, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['../../styles.css', 'header.component.css']
})
export class HeaderComponent implements OnInit{
  isAdmin : boolean = false;
  constructor(private authService: AuthService) { }

  scrolled: boolean = false;


  ngOnInit() {
    this.isAdmin = Boolean(this.authService.isAdmin());
    console.log("Es admin" +this.authService.isAdmin());
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
