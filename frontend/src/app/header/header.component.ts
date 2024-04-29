import { Component, HostListener } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['../../styles.css', 'header.component.css']
})
export class HeaderComponent {

  scrolled: boolean = false;

  @HostListener('window:scroll', [])
  onWindowScroll() {
    if (window.scrollY > 0) {
      this.scrolled = true;
    } else {
      this.scrolled = false;
    }
  }

}
