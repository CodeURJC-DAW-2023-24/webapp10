import { BetsService } from '../services/bets.service';
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
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
  wonPercentage: number = 0;
  lostPercentage: number = 0;
  pendingPercentage: number = 0;

  // Add the following properties for chart data
  chartData: any[] = [];
  colorScheme: any = {
    domain: ['#33FF57', '#FF5733', '#CCCCCC']
  };

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
      this.bets = bets.filter((bet: any) => !bet.event.finished);
      this.betsFinished = bets.filter((bet: any) => bet.event.finished);
      this.hasBets = this.bets.length > 0;
      this.hasBetsFinished = this.betsFinished.length > 0;
      this.calculateTotals();
      this.calculatePercentages();

      // Update chart data
      this.chartData = [
        { name: 'Ganadas', value: this.wonPercentage },
        { name: 'Perdidas', value: this.lostPercentage },
        { name: 'Pendientes', value: this.pendingPercentage }
      ];
    });
  }

  private calculateTotals(): void {
    this.totalWinningAmount = this.betsFinished.reduce((acc, bet) => acc + bet.winning_amount, 0);
    this.totalBet = this.bets.reduce((acc, bet) => acc + bet.bet_amount, 0);
    this.totalBenefit = this.betsFinished.reduce((acc, bet) => acc + bet.winning_amount - bet.bet_amount, 0);
  }

  private calculatePercentages(): void {
    const totalFinished = this.betsFinished.length;
    const totalPending = this.bets.length;

    if (totalFinished > 0 || totalPending > 0) {
      const wonCount = this.betsFinished.filter(bet => bet.winning_amount > 0).length;
      const lostCount = totalFinished - wonCount;

      this.wonPercentage = (wonCount / (totalFinished + totalPending)) * 100;
      this.lostPercentage = (lostCount / (totalFinished + totalPending)) * 100;
      this.pendingPercentage = (totalPending / (totalFinished + totalPending)) * 100;
    } else {
      this.wonPercentage = 0;
      this.lostPercentage = 0;
      this.pendingPercentage = 0;
    }
  }

  downloadPdf(): void {
    const userId = this.user.id;
    const betData = this.bets;

    this.pdfGeneratorService.generatePdf(userId, betData);
  }
}
