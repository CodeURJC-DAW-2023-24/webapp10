import { User } from "./user.model";

export interface Wallet {
  id?: number;
  card_number: string;
  expired_date: string;
  cvv: string;
  owner: string;
  money: number;
}
