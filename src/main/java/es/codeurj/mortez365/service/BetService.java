package es.codeurj.mortez365.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lowagie.text.List;

import es.codeurj.mortez365.model.Bet;
import es.codeurj.mortez365.repository.BetRepository;

@Service
public class BetService {

    private BetRepository betRepository;

    public BetService(BetRepository betRepository) {
        super();
        this.betRepository = betRepository;
    }

    public Bet getBetById(long id) {
        return betRepository.findById(id).get();
    }

    public Bet saveBet(Bet bet) {

        return betRepository.save(bet);
    }

    public void deleteBet(long id) {
        betRepository.deleteById(id);
    }

    public Optional<Bet> findBetById(long id) {
        return betRepository.findById(id);
    }

    public Collection <Bet> findAllBets() {
        return betRepository.findAll();
    }


    
}
