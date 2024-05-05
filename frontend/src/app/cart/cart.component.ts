import { BetsService } from '../services/bets.service';
import { Component, OnInit } from '@angular/core';
import {AuthService} from "../services/auth.service";
import { PdfGeneratorService } from '../services/pdfGenerator.service';

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
  user: any;

  constructor(
    private authService: AuthService,
    private betsService: BetsService,
    private pdfGeneratorService: PdfGeneratorService
  ) {}

  ngOnInit(): void {
    this.user = this.authService.getUserDetails();
    if (this.authService.isLogged()) {
      this.loadBetsData();
    }
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

  downloadPdf(): void {
    const userId = this.user.id;
    const betData = this.bets;

    this.pdfGeneratorService.generatePdf(userId, betData);
  }
}
