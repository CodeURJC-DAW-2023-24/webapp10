package es.codeurj.mortez365.service;


import es.codeurj.mortez365.model.Wallet;
import es.codeurj.mortez365.repository.WalletRepository;
import org.springframework.stereotype.Service;


import es.codeurj.mortez365.model.User;
import es.codeurj.mortez365.repository.UserRepository;

import java.util.Date;

@Service
public class WalletService {
    private WalletRepository walletRepository;

    //The UserSevice class is used to save the user in the database.
    public WalletService(WalletRepository walletRepository) {
        super();
        this.walletRepository = walletRepository;
    }

    public Wallet save(Wallet wallet) {
        return walletRepository.save(wallet);
    }
}
