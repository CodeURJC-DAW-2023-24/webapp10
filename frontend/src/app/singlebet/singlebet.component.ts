import { Component, ElementRef, ViewChild } from '@angular/core';
import { EventsService } from '../services/events.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-singlebet',
  templateUrl: './singlebet.component.html',
  styleUrl: './singlebet.component.css'
})
export class SinglebetComponent {
  event: any;
  feeT:number = 0;
  feeL:number = 0;

  @ViewChild('selectedBetInput') selectedBetInput!: ElementRef;

  constructor(private router: Router,activatedRoute: ActivatedRoute, private eventsService: EventsService) {
    let id = activatedRoute.snapshot.params['id'];

    eventsService.getEventById(id).subscribe(
      event => {
        this.event = event;
        this.feeT = 1.7;
        this.feeL = +(3.5 - event.fee).toFixed(2);
      },
      error => console.error(error)
    );

  }

  selectBet(betType: string, event: Event) {
    console.log("LLEGA");
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

}



