import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Event } from "../models/event.model";

@Injectable({ providedIn: "root" })
export class EventsService {
  private baseUrl = "/api/events/";

  constructor(private http: HttpClient) {}

getEvents(page: number, pageSize: number): Observable<any> {
    const params = { page: page.toString(), pageSize: pageSize.toString() };
    return this.http.get<any>(`${this.baseUrl}`, { params });
  }
  getEventById(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }

  createEvent(event: Event): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}`, event);
  }

  updateEvent(id: number, event: Event): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/${id}`, event);
  }

  deleteEvent(id: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/${id}`);
  }


  getEventImage(id: string): Observable<Blob> {
    return this.http.get(`${this.baseUrl}image/${id}`, { responseType: 'blob' });
  }

  addEventImage(id: string, image: File): Observable<Blob> {
    const formData = new FormData();
    formData.append("image", image);
    return this.http.post<any>(`${this.baseUrl}image/${id}`, formData);
  }

  deleteEventImage(id: string): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/image/${id}`);
  }

  updateEventImage(id: string, image: File): Observable<any> {
    const formData = new FormData();
    formData.append("image", image);
    return this.http.put<any>(`${this.baseUrl}/image/${id}`, formData);
  }
}