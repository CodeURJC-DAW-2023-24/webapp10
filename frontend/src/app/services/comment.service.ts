import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private baseUrl = '/api';

  constructor(private http: HttpClient, private authService: AuthService) { }

  addComment(id: number, commentText: string): Observable<any> {
    const body = new URLSearchParams();
    body.set('text', commentText);
    return this.http.post<any>(`${this.baseUrl}/single-product/${id}/comment`, body.toString());
  }

  editComment(eventId: number, id: number, newComment: string): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/editComment/${eventId}/${id}`, { content: newComment });
  }

  deleteComment(eventId: number, id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/comment/${eventId}/${id}/delete`);
  }
}
