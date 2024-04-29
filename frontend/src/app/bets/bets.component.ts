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
  events: any [] = [];
  currentPage = 0;
  pageSize = 10;
  ngOnInit(): void {
   this.loadEvents();
      
    };
    loadEvents() {
      this.eventsService.getEvents(this.currentPage, this.pageSize).subscribe((data) => {
        this.events = data.content;
        console.log(this.events);
      });
  }

  loadMoreEvents() {
    this.currentPage++;
    this.loadEvents();
  }

   
  }




