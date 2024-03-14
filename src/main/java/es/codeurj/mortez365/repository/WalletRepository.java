package es.codeurj.mortez365.repository;

import es.codeurj.mortez365.model.Bet;
import es.codeurj.mortez365.model.Event;
import es.codeurj.mortez365.model.User;
import es.codeurj.mortez365.model.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


//The WalletRepository interface is used to save the wallet in the database.
public interface WalletRepository extends JpaRepository<Wallet, Long>{

    Wallet findByUser(User user);

}