import { Component, OnInit } from '@angular/core';
import { EventsService } from '../services/events.service';
import { HttpClient } from '@angular/common/http';
@Component({
  selector: 'app-bets',
  templateUrl: './bets.component.html',
  styleUrl: './bets.component.css'
})
export class BetsComponent{
  constructor(private eventsService: EventsService) {}
  events: any;
  ngOnInit(): void {
    this.eventsService.getEvents().subscribe((data) => {
      this.events = data;
      console.log(this.events);
    });
  }


  }




