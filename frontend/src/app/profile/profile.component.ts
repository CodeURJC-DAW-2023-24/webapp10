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

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit() {
    if (this.authService.isLogged()) {
      this.user = this.authService.getUserDetails(); 
  
      this.authService.getUserImage(this.authService.getId()).subscribe(
        (imageBlob: Blob) => {
          const objectUrl = URL.createObjectURL(imageBlob);
          this.image = objectUrl;
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
 
  selectedImage!: File;

  
  onFileSelected(event: any): void {
    this.selectedImage = event.target.files[0];
  }

  uploadImage(): void {
    const userId = 1;
    if (this.selectedImage) {
      this.authService.updateUserImage(userId, this.selectedImage)
        .subscribe(
          (          response: any) => {
            console.log('Image uploaded', response);
           
          },
          (          error: any) => {
            console.error('Error uploading image', error);
          }
        );
    }
  }
}