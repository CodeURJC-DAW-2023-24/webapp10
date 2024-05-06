import { User } from './user.model';
import { Wallet } from './wallet.model';

export interface RegistrationDataDTO {
  user: User;
  wallet: Wallet;
}
