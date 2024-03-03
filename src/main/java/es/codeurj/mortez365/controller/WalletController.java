package es.codeurj.mortez365.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import es.codeurj.mortez365.model.User;
import es.codeurj.mortez365.model.Wallet;
import es.codeurj.mortez365.repository.UserRepository;
import es.codeurj.mortez365.service.UserSevice;

@Controller
@RequestMapping("/wallet")
public class WalletController {
    @Autowired
    private UserSevice userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/addFunds")
    public String addFundsToWallet(@RequestParam double amountToAdd, Principal principal) {
        // Get the current user
        Optional<User> user = userRepository.findByName(principal.getName());

        if (user.isPresent()) {
            // Update the user's money
            user.get().setMoney(user.get().getMoney() + amountToAdd);

            // Save the updated user
            userService.save(user.get());
            System.out.println("Funds added to wallet");
            System.out.println("New wallet balance: " + user.get().getMoney());
        }
        return "redirect:/wallet";
    }
}