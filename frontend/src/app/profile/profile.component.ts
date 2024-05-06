import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {


  user: any;
  image: any;
  selectedFile!: File;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit() {
    if (this.authService.isLogged()) {
      this.user = this.authService.getUserDetails();
  
      this.authService.getUserImage(this.authService.getId()).subscribe(
        (imageBlob: Blob) => {
          if (imageBlob.size > 0) {
            const objectUrl = URL.createObjectURL(imageBlob);
            this.image = objectUrl;
            console.log("IMAGEN: ", this.image);
          } else {
            this.image = null;
          }
        },
        (error) => {
          console.error('Error getting user image', error);
        }
      );
    }
  }
    logOut() {
    this.authService.logOut();
    this.router.navigate(['/home']);
  }

  onFileSelected(event: any): void {
    if (event.target.files && event.target.files.length > 0) {
      const fileInput = event.target;
      const fileName = fileInput.files[0].name;
      const fileLabel = document.getElementById('file-label');
      if (fileLabel) {
        fileLabel.innerText = fileName;
      }
      this.selectedFile = event.target.files[0];
    }
    console.log("SE HA CAMBIADO EL ARCHIVO A: ", this.selectedFile.name);
  }

  uploadImage() {
    if (this.selectedFile) {
      this.authService.updateUserImage(this?.user?.id, this.selectedFile).subscribe(
        response => {
          this.authService.getUserImage(this.user.id).subscribe(
            (imageBlob: Blob) => {
              const objectUrl = URL.createObjectURL(imageBlob);
              this.image = objectUrl;
            },
            (error) => {
              console.error('Error getting user image', error);
            }
          );
        },
        error => {
          console.log("Error en el put de imagen: ", error);
        }
      );

      // After changing the image, keep the text intact as before
      const fileLabel = document.getElementById('file-label');
      if (fileLabel) {
        fileLabel.innerText = "Elegir archivo";
      }
    }
  }
}
