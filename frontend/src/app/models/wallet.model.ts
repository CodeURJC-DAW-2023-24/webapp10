import { User } from "./user.model";

export class Wallet {
  id?: number;
  card_number: string;
  expired_date: string;
  cvv: string;
  owner: string;
  money: number;
  user?: User;

  constructor() {
    this.card_number = '';
    this.expired_date = '';
    this.cvv = '';
    this.owner = '';
    this.money = 100;
  }

  setUser(u: User) {
    this.user = u;
  }
}
