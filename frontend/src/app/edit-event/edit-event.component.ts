import { Component } from '@angular/core';
import { EventsService } from '../services/events.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-edit-event',

  templateUrl: './edit-event.component.html',
  styleUrl: './edit-event.component.css'
})
export class EditEventComponent {
  event: any;

  constructor(activatedRoute: ActivatedRoute, private eventsService: EventsService) {
    let id = activatedRoute.snapshot.params['id'];
    if (id) {
      eventsService.getEventById(id).subscribe(
        event => this.event = event,
        error => console.error(error)
      );
    }
0
    
  }

  save (){
    this.eventsService.updateEvent(this.event.id ,this.event).subscribe(
      event => console.log(event),
      error => console.error(error)
    );
  }

 
}
