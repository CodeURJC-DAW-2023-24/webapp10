import { Component } from '@angular/core';
import { EventsService } from '../services/events.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-edit-event',

  templateUrl: './edit-event.component.html',
  styleUrl: './edit-event.component.css'
})
export class EditEventComponent {
  event: any;

  constructor(private router: Router,activatedRoute: ActivatedRoute, private eventsService: EventsService) {
    let id = activatedRoute.snapshot.params['id'];
    if (id) {
      eventsService.getEventById(id).subscribe(
        event => this.event = event,
        error => console.error(error)
      );
    }


  }

  save (){
    this.eventsService.updateEvent(this.event.id ,this.event).subscribe(
      event => console.log(event),
      error => console.error(error)
    );
    this.router.navigate(['/betsadmin']);
  }


}
