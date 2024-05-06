import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Comment, C } from '../models/comment.model';


@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private baseUrl = '/api/comment';

  constructor(private http: HttpClient) { }

  getComment(id: number): Observable<Comment> {
    return this.http.get<Comment>(`${this.baseUrl}/${id}`);
  }

  getAllComments(): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.baseUrl}/`);
  }

  getCommentsByEvent(eventId: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.baseUrl}/event/${eventId}`);
  }

  createComment(comment: C, eventId: number): Observable<any> {
    console.log("LLEGA");
    return this.http.post<any>(`${this.baseUrl}/${eventId}`, comment);
  }

  deleteComment(id: number): Observable<any> {
    console.log(id);
    return this.http.delete<any>(`${this.baseUrl}/${id}`);
  }

  replaceComment(id: number, newComment: C): Observable<any> {
    return this.http.put<any>(`${this.baseUrl}/${id}`, newComment);
  }
}
