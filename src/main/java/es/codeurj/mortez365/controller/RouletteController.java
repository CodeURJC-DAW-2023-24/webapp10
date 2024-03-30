package es.codeurj.mortez365.controller;

import es.codeurj.mortez365.model.User;
import es.codeurj.mortez365.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/updateValue")
public class RouletteController {

    @Autowired
    private UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(AppController.class);

    //Method to update the value of the money of the user
    @PostMapping
    public String updateValue(@RequestBody Integer newBankValue, HttpServletRequest request) {
        String name = request.getUserPrincipal().getName();
        log.info("UPDATE VALUE LLEGA HASTA AQUI");
        User user = userRepository.findByUsername(name).orElseThrow();
        user.getWallet().setMoney(newBankValue);
        userRepository.save(user);
        return "/roulette";
    }
}
