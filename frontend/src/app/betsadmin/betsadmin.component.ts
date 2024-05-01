import { Component } from '@angular/core';
import { EventsService } from '../services/events.service';
import { Event } from '../models/event.model';
@Component({
  selector: 'app-betsadmin',
  templateUrl: './betsadmin.component.html',
  styleUrl: './betsadmin.component.css'
})
export class BetsadminComponent {
    event!: Event;
    constructor(private eventsService: EventsService) {}
    events: any [] = [];
    currentPage = 0;
    pageSize = 10;
    ngOnInit(): void {
     this.loadEvents();
        
      };
      loadEvents() {
        this.eventsService.getEvents(this.currentPage, this.pageSize).subscribe((data) => {
          this.events = data.content.slice(0, this.pageSize);
          console.log(this.events);
          console.log(data);
          console.log(this.pageSize)
        });
    }
  loadMore() {
    this.pageSize += 10;
    this.loadEvents();
  }

  
  saveEvent() {
    this.eventsService.createEvent(this.event).subscribe(
      event => console.log(event),
      error => console.error(error)
    );
  }
     

}
