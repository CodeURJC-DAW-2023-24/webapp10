package es.codeurj.mortez365.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;

@Entity
@Data
@Table(name = "USER")
public class User {

    @Id
    @Column(name = "ID")
    private long id;

    @PrimaryKeyJoinColumn(name = "WALLET")
    @OneToOne
    private Wallet wallet;

    @Column(name = "BETS")
    @OneToMany(mappedBy = "user")
    private ArrayList<Bet> bets;
}