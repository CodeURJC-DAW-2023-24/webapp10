import { BetsService } from './../services/bets.service';
import { Component, Injectable, OnInit } from '@angular/core';
import { EventsService } from '../services/events.service';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Subscription, interval } from 'rxjs';

interface Bet {
  id: number;
  result: string;
  bet_amount: number;
  winning_amount: number;
  fee: number;
  event: {
    id: number;
    name: string;
    fee: number;
    finalResult: string;
    finished: boolean;
  };
}

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  bets: Bet[] = [];
  betsFinished: Bet[] = [];
  hasBets: boolean = false;
  hasBetsFinished: boolean = false;
  totalWinningAmount: number = 0;
  totalBet: number = 0;
  totalBenefit: number = 0;

  constructor(private router: Router, private eventsService: EventsService, private betsService: BetsService) {}

  ngOnInit(): void {
    this.loadBetsData();
  }

  private loadBetsData(): void {
    this.betsService.getBets().subscribe((bets: Bet[]) => {
      console.log(bets);
      this.bets = bets.filter((bet: any) => !bet.event.finished);
      this.betsFinished = bets.filter((bet: any) => bet.event.finished);
      this.hasBets = this.bets.length > 0;
      this.hasBetsFinished = this.betsFinished.length > 0;
      this.calculateTotals();
    });
  }


  private calculateTotals(): void {
    this.totalWinningAmount = this.betsFinished.reduce((acc, bet) => acc + bet.winning_amount, 0);
    this.totalBet = this.bets.reduce((acc, bet) => acc + bet.bet_amount, 0);
    this.totalBenefit = this.betsFinished.reduce((acc, bet) => acc + bet.winning_amount - bet.bet_amount, 0);
  }
}
