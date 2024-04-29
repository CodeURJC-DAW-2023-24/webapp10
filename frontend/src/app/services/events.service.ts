import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({ providedIn: "root" })
export class EventsService {
  private baseUrl = "/api/events/";

  constructor(private http: HttpClient) {}

  getEvents(page: number, pageSize: number): Observable<any> {
    const params = { page: page.toString(), pageSize: pageSize.toString() };
    return this.http.get<any>(`${this.baseUrl}`, { params });
  }
  getEventById(id: string): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }

  createEvent(event: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}`, event);
  }

  updateEvent(id: string, event: any): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/${id}`, event);
  }

  deleteEvent(id: string): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/${id}`);
  }
}