import { BetsService } from './../services/bets.service';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { EventsService } from '../services/events.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Result } from '../models/result.model';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-singlebet',
  templateUrl: './singlebet.component.html',
  styleUrl: './singlebet.component.css'
})
export class SinglebetComponent {
  event: any;
  feeT:number = 0;
  feeL:number = 0;
  betAmount: number = 0;
  id!: number;
  user: any;

  @ViewChild('selectedBetInput') selectedBetInput!: ElementRef;

  constructor(private router: Router,activatedRoute: ActivatedRoute, private eventsService: EventsService, private betsService: BetsService, private authService: AuthService) {
    this.id = activatedRoute.snapshot.params['id'];

    eventsService.getEventById(this.id).subscribe(
      event => {
        this.event = event;
        this.feeT = 1.7;
        this.feeL = +(3.5 - event.fee).toFixed(2);
      },
      error => console.error(error)
    );
  }


    ngOnInit() {
      if (this.authService.isLogged()) {
        this.user = this.authService.getUserDetails();
        console.log("USUARIO: ", this.user);
  
   
  }
    }
  comprobarApuesta(): boolean {
    if (!this.isBetSelected()) {
      alert('Debes seleccionar una apuesta.');
      return false;
    }
    if (this.betAmount <= 0) {
      alert('La apuesta debe ser mayor que 0.');
      return false;
    }
    console.log("LLEGA");
    const r: Result = this.selectResult();
    console.log("LLEGA");
    this.betsService.createBet({
      bet_amount: this.betAmount,
      result: r,
    }, this.event.id).subscribe(() => {
      alert('Apuesta creada');
      this.router.navigate(['/home']);
      return true;
    }, error => {
      console.error('Error al crear la apuesta: ', error);
      return false;
    });
    return true;
  }

  selectBet(betType: string, event: Event) {
    const betButtons = document.querySelectorAll('.bordered-btn-bets');

    // Deseleccionar todos los botones
    betButtons.forEach(button => {
      button.classList.remove('selected');
    });

    // Seleccionar el botón actual
    const target = event.target as HTMLElement;
    target.classList.add('selected');

    // Asignar el tipo de apuesta al input
    this.selectedBetInput.nativeElement.value = betType;
  }


  isBetSelected(): boolean {
    // Comprueba si se ha seleccionado una apuesta
    const selectedBet = document.getElementById('selected-bet') as HTMLInputElement;

    return selectedBet.value !== '';
  }

  selectResult(): Result {
    if(this.selectedBetInput.nativeElement.value == "Victoria"){
      return Result.WIN;
    } else if(this.selectedBetInput.nativeElement.value == "Empate"){
      return Result.TIE;
    } else{
      return Result.LOSE;
    }
  }
}



