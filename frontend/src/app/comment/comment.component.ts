import { Component, OnInit } from '@angular/core';
import { Comment } from '../models/comment.model';
import { CommentService } from '../services/comment.service';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit {

  comments: Comment[] = [];

  constructor(private commentService: CommentService) { }

  ngOnInit(): void {
    this.loadComments();
  }

  loadComments(): void {
    this.commentService.getAllComments()
      .subscribe(comments => {
        this.comments = comments;
      });
  }

//To do: adjust data.

  addComment(content: string): void {
    const newComment: Comment = { id: 0, content, userId: 1, eventId: 1 };
    this.commentService.createComment(newComment, 1)
      .subscribe(comment => {
        this.comments.push(comment);
      });
  }

  deleteComment(id: number): void {
    this.commentService.deleteComment(id)
      .subscribe(() => {
        this.comments = this.comments.filter(comment => comment.id !== id);
      });
  }

}
