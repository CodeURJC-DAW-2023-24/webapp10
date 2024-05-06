import { Wallet } from "./wallet.model";

export class User {
  id?: number;
  name: string;
  roles: string[];
  username: string;
  password: string;
  firstsurname: string;
  secondsurname: string;
  email: string;
  birthdate: Date;
  nationality: string;
  dni: string;
  adress: string;
  postcode: number;
  telephone: number;
  imageFile?: File;
  image?: Blob;
  wallet?: Wallet;

  constructor() {
    this.name = '';
    this.roles = [];
    this.username = '';
    this.password = '';
    this.firstsurname = '';
    this.secondsurname = '';
    this.email = '';
    this.birthdate = new Date();
    this.nationality = '';
    this.dni = '';
    this.adress = '';
    this.postcode = 0;
    this.telephone = 0;
    this.imageFile = new File([], "", undefined);
  }

  setWallet(w: Wallet) {
    this.wallet = w;
  }

  getImageFile() {
    return this.imageFile;
  }

  setImage(b: Blob) {
    this.image = b;
  }
}
