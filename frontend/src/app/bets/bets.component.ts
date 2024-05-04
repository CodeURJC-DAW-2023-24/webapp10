import { Component, OnInit, OnDestroy } from '@angular/core';
import { EventsService } from '../services/events.service';
import { HttpClient } from '@angular/common/http';
import { Subscription, interval } from 'rxjs';

@Component({
  selector: 'app-bets',
  templateUrl: './bets.component.html',
  styleUrl: './bets.component.css'
})
export class BetsComponent implements OnInit, OnDestroy{
  constructor(private eventsService: EventsService) {}
  events: any [] = [];
  currentPage = 0;
  pageSize = 10;
  private timerSubscription!: Subscription;

  ngOnInit(): void {
   this.loadEvents();
   this.startTimer();
  };
  ngOnDestroy(): void {
    this.stopTimer();
  }

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

  private startTimer(): void {
    this.timerSubscription = interval(30000).subscribe(() => {
      this.loadEvents();
    });
  }

  private stopTimer(): void {
    if (this.timerSubscription) {
      this.timerSubscription.unsubscribe();
    }
  }
}



