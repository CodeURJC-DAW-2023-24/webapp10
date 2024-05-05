import { Component, Input, OnInit } from '@angular/core';
import { Comment } from '../models/comment.model';
import { CommentService } from '../services/comment.service';
import { AuthService } from '../services/auth.service';


@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit {

  comment: Comment | undefined;
  comments: Comment[] = [];
  commentText: string = '';
  name: string = '';
  isEditing: boolean = false;
  privileged: boolean = false;

  @Input() eventId!: number;


  constructor(private commentService: CommentService, private authService: AuthService) { }

  ngOnInit(): void {
    this.privileged = this.authService.isAdmin();
    this.loadComments();
  }

  loadComments(): void {
    this.commentService.getCommentsByEvent(this.eventId)
      .subscribe(comments => {
        this.comments = comments;
      });
  }

  addComment(): void {
    const newComment: Comment = { id: 0, content: this.commentText, user: '', eventId: this.eventId };
    this.commentService.createComment(newComment, this.eventId)
      .subscribe(comment => {
        this.comments.push(comment);
        this.loadComments();
      });
  }

  deleteComment(id: number): void {
    this.commentService.deleteComment(id)
      .subscribe(() => {
        this.comments = this.comments.filter(comment => comment.id !== id);
      });
  }

  replaceComment(id: number, newCommentText: string): void {
    const updatedComment: Comment = {
      id,
      content: newCommentText,
      user: '',
      eventId: this.eventId
    };
    this.commentService.replaceComment(id, updatedComment)
      .subscribe(updatedComment => {
        const index = this.comments.findIndex(comment => comment.id === updatedComment.id);
        if (index !== -1) {
          this.comments[index] = updatedComment;
        }
      });
  }
  editComment() {
    this.isEditing = true;
  }
}
