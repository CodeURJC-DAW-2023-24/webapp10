import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { User } from '../models/user.model';
import { Wallet } from '../models/wallet.model';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['../../styles.css', './register.component.css']
})
export class RegisterComponent {

  user : User;
  wallet : Wallet;

  constructor(private authService: AuthService, private router: Router) {
    this.user = new User();
    this.wallet = new Wallet();
  }

  registerUser(){

    this.wallet.owner = this.user.name;
    this.user.setWallet(this.wallet);
    let self = this;
    this.authService.register(this.user).subscribe(
      user => {
        console.log(user);
       
      },
      error => {
        console.log(error);
      }
     
    );
    this.router.navigate(['/home']);
  }


    




    /*
    let Resource image = new ClassPathResource(user.getImageFile());
    user.setImage(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));*/
    //this.wallet.setUser(this.user);

    

  private fileToBlob(file: File): Promise<Blob> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();

      // Definir la función de callback para cuando se complete la lectura del archivo
      reader.onload = () => {
        const arrayBuffer = reader.result as ArrayBuffer;
        const blob = new Blob([arrayBuffer], { type: file.type });
        resolve(blob);
      };

      // Definir la función de callback para manejar errores
      reader.onerror = () => {
        reject(new Error('Error al leer el archivo'));
      };
    });
  }




}
