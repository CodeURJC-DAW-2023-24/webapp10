import { Component } from '@angular/core';

@Component({
  selector: 'app-header-no-transparent',
  templateUrl: './header-no-transparent.component.html',
  styleUrls: ['../../styles.css', 'header-no-transparent.component.css']
})
export class HeaderNoTransparentComponent {

  ngOnInit() {
    window.scrollTo(0, 0);
  }

}
