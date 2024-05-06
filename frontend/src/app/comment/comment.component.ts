import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { Comment, C } from '../models/comment.model';
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

  @ViewChild('commentTex') commentTextRef: ElementRef | undefined;

  constructor(private commentService: CommentService, private authService: AuthService) { }

  ngOnInit(): void {
    this.privileged = this.authService.isAdmin();
    this.loadComments();
    console.log(this.eventId);
  }

  loadComments(): void {
    this.commentService.getCommentsByEvent(this.eventId)
      .subscribe(comments => {
        this.comments = comments;
      });
  }

  addComment(): void {
    console.log("EVENTO ID: ", this.eventId);
    const newComment: C = { content: this.commentText };
    this.commentService.createComment(newComment, this.eventId)
      .subscribe(comment => {
        this.comments.push(comment);
        this.loadComments();
      });
  }

  deleteComment(id: number): void {
    if (confirm('¿Estás seguro de que quieres borrar este comentario?')) {
    this.commentService.deleteComment(id).subscribe(
      c => {
        console.log(c);
        this.comments = this.comments.filter(comment => comment.id !== id);
      },
      error => console.error(error)
    );
      this.loadComments();
    }
  }

  replaceComment(id: number, newCommentText: string): void {
    console.log(newCommentText);
    console.log(this.commentTextRef?.nativeElement.value);
    const updatedComment: C = {

      content: this.commentTextRef?.nativeElement.value

    };
    this.commentService.replaceComment(id, updatedComment)
      .subscribe(updatedComment => {
        const index = this.comments.findIndex(comment => comment.id === updatedComment.id);
        if (index !== -1) {
          this.comments[index] = updatedComment;
        }
      });
    this.isEditing = false;
  }

  editComment(id: number) {
    this.isEditing = true;
  }
}
