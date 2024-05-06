import { Result } from "./result.model";

export interface Bet {
  id?: number;
  bet_amount: number;
  winning_amount?: number;
  profit?: number;
  event?: Event;
  result?: Result;

}
