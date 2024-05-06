import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
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

  constructor(private authService: AuthService) {
    this.user = new User();
    this.wallet = new Wallet();
  }

  registerUser(){

    this.wallet.owner = this.user.name;
    this.user.setWallet(this.wallet);
    let self = this;

    if (this.user.imageFile) {
      console.log("NOMBRE DEL ARCHIVO: ", this.user.imageFile.name);
      let fT = self.user.imageFile;

      if (fT) {
        let reader = new FileReader();
        reader.onloadend = function() {
          let blob = new Blob([new Uint8Array((reader.result as ArrayBuffer))], {type: fT.type});
          console.log("BLOB EN EL READER: ", blob);
          self.user.setImage(blob);
          console.log("Blob: ", self.user.image);
          // Ahora la variable blob contiene el Blob
        }

        reader.readAsArrayBuffer(fT);
      }

    }




    /*
    let Resource image = new ClassPathResource(user.getImageFile());
    user.setImage(BlobProxy.generateProxy(image.getInputStream(), image.contentLength()));*/
    //this.wallet.setUser(this.user);

    this.authService.register(this.user).subscribe(
      user => {
        console.log(user);
      },
      error => {
        console.log(error);
      }
    );
  }

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


  onFileSelected(event: any) {
    this.user.imageFile = event.target.files[0];
  }

}
