package es.codeurj.mortez365.DTO;

import es.codeurj.mortez365.model.User;
import es.codeurj.mortez365.model.Wallet;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class RegistrationDataDTO {
    
    private User user;
    private Wallet wallet;

    public RegistrationDataDTO(User user, Wallet wallet) {
        this.user = user;
        this.wallet = wallet;
    }

}



