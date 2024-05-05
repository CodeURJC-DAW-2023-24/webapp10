import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Comment } from '../models/comment.model';


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

  createComment(comment: Comment, eventId: number): Observable<Comment> {
    console.log("LLEGA")
    return this.http.post<Comment>(`${this.baseUrl}/${eventId}`, comment);
  }

  deleteComment(id: number): Observable<Comment> {
    console.log(id);
    return this.http.delete<Comment>(`${this.baseUrl}/${id}`);
  }

  replaceComment(id: number, newComment: Comment): Observable<Comment> {
    return this.http.put<Comment>(`${this.baseUrl}/${id}`, newComment);
  }
}
