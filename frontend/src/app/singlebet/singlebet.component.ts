import { Component } from '@angular/core';
import { EventsService } from '../services/events.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-singlebet',
  templateUrl: './singlebet.component.html',
  styleUrl: './singlebet.component.css'
})
export class SinglebetComponent {
  event: any;

  constructor(private router: Router,activatedRoute: ActivatedRoute, private eventsService: EventsService) {

    let id = activatedRoute.snapshot.params['id'];
    eventsService.getEventById(id).subscribe( 
      event => this.event = event,
      error => console.error(error)
    );

  }
   }

 

