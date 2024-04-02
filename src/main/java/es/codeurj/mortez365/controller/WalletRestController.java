package es.codeurj.mortez365.controller;


import es.codeurj.mortez365.DTO.UserDataDTO;
import es.codeurj.mortez365.DTO.WalletDataDTO;
import es.codeurj.mortez365.model.User;
import es.codeurj.mortez365.model.Wallet;
import es.codeurj.mortez365.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/wallets")
public class WalletRestController {

    @Autowired
    private WalletService walletService;


    @GetMapping("/")
    public ResponseEntity<Iterable<WalletDataDTO>> getWallets() {
        List<Wallet> wallets =  walletService.findAll();
        if (wallets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Collection<WalletDataDTO> walletList = new ArrayList<>();
        for (Wallet wallet : wallets) {
            WalletDataDTO walletDto = new WalletDataDTO(wallet.getOwner(), wallet.getMoney(), wallet.getCard_number());
            walletList.add(walletDto);
        }
        return new ResponseEntity<>(walletList, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<WalletDataDTO> getWallet(@PathVariable Long id){
        Optional<Wallet> wallet = walletService.findById(id);
        if (wallet.isPresent()) {
            WalletDataDTO walletDTO = new WalletDataDTO(wallet.get().getOwner(), wallet.get().getMoney(), wallet.get().getCard_number());
            return ResponseEntity.ok(walletDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //No delete method because no sense that is posible to delete a wallet same happends with post method

    @PutMapping("/{id}")
    public ResponseEntity<Wallet> replaceWallet(@RequestBody Wallet newWallet, @PathVariable Long id) {
        Optional<Wallet> wallet = walletService.findById(id);

        if(wallet.isPresent()){
            newWallet.setId(id);
            walletService.save(newWallet);
            URI location = fromCurrentRequest().path("/{id}").buildAndExpand(newWallet.getId()).toUri();
            return ResponseEntity.ok().location(location).body(newWallet);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
