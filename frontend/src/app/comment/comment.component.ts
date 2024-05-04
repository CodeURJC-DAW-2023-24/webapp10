import { Component } from '@angular/core';
import { CommentService } from '../services/comment.service';


@Component({
  selector: 'app-comment',
  standalone: true,
  imports: [],
  templateUrl: './comment.component.html',
  styleUrl: './comment.component.css'
})
export class CommentComponent {

  constructor(private commentService: CommentService) { }

  addComment(id: number, commentText: string): void {
    this.commentService.addComment(id, commentText)
      .subscribe(
        response => {
          console.log('Comentario agregado:', response);
        },
        error => {
          console.error('Error al agregar comentario:', error);
        }
      );
  }

  editComment(eventId: number, id: number, newComment: string): void {
    this.commentService.editComment(eventId, id, newComment)
      .subscribe(
        response => {
          console.log('Comentario editado:', response);

        },
        error => {
          console.error('Error al editar comentario:', error);
        }
      );
  }

  deleteComment(eventId: number, id: number): void {
    this.commentService.deleteComment(eventId, id)
      .subscribe(
        response => {
          console.log('Comentario eliminado:', response);
        },
        error => {
          console.error('Error al eliminar comentario:', error);
        }
      );
  }
}
