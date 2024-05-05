import { Component } from '@angular/core';
import { EventsService } from '../services/events.service';
import { Event } from '../models/event.model';
@Component({
  selector: 'app-betsadmin',
  templateUrl: './betsadmin.component.html',
  styleUrl: './betsadmin.component.css'
})
export class BetsadminComponent {
    event : Event = {} as Event;
    selectedFile: File | undefined;
    constructor(private eventsService: EventsService) {}
    events: any [] = [];

    currentPage = 0;
    pageSize = 10;
    ngOnInit(): void {
     this.loadEvents();
        
      };

      loadEvents() {
        this.eventsService.getEvents(this.currentPage, this.pageSize).subscribe((data) => {
          this.events = data.content.slice(0, this.pageSize);
          this.events.forEach(event => {
            this.eventsService.getEventImage(event.id).subscribe(
           (imageBlob: Blob) => {
              const objectUrl = URL.createObjectURL(imageBlob);
              event.image = objectUrl;
              console.log("imagen",event.image);
            },
            (error) => {
              console.error('Error getting user image', error);
            }
          );
          });
        
          console.log(this.events);
          console.log(data);
          console.log(this.pageSize)
        });
      }
  loadMore() {
    this.pageSize += 10;
    this.loadEvents();
  }

  onFileSelected(event: any) {
    this.selectedFile = <File>event.target.files[0];
  }


  
  saveEvent() {
    this.eventsService.createEvent(this.event).subscribe(
      event => {
        console.log(event);
        if (this.selectedFile) {
          this.eventsService.addEventImage(event.id, this.selectedFile).subscribe(
            res => console.log("imagen subida dlocos"),
            error => console.error(error)
          );
        }
      },
      error => console.error(error)
    );
  }

  deleteEvent(id: number) {
    if (confirm('¿Estás seguro de que quieres borrar este evento?')) {
        this.eventsService.deleteEvent(id).subscribe(
            event =>{
              console.log(event);
              location.reload();
            },
            error => console.error(error)
        );
    }

    
}
     

}
