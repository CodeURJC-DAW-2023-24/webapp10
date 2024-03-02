package es.codeurj.mortez365.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import es.codeurj.mortez365.model.User;
import es.codeurj.mortez365.model.Wallet;
import es.codeurj.mortez365.repository.UserRepository;
import es.codeurj.mortez365.service.UserSevice;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
   private UserSevice userService;

   private UserRepository userRepository;

   @PostMapping("/addFunds")
   public String addFundsToWallet(@RequestParam double amountToAdd, Principal principal) {
       // Get the current user
       Optional<User> user = userRepository.findByName(principal.getName());
   
       if (user.isPresent()) {
           // Update the wallet
           Wallet wallet = user.get().getWallet();
           wallet.setMoney(wallet.getMoney() + amountToAdd);
   
           // Save the updated wallet
           userService.save(user.get());
   
           return "Fondos añadios correctamente";
       } else {
           return "No se han podido añadir los fondos";
       }
   }
}