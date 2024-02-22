package es.codeurj.mortez365.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;

@Entity
@Data
@Table(name = "WALLET")
public class Wallet {

    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "MONEY")
    private double money;

    @Column(name = "USER")
    @OneToOne(mappedBy = "wallet")
    private User user;
}
