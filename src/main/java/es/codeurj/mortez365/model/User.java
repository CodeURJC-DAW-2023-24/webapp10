package es.codeurj.mortez365.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Entity
@Data
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    @Getter
    @Column(name = "ID")
    private long id;

    @Setter
    @Getter
    @PrimaryKeyJoinColumn(name = "WALLET")
    @OneToOne
    private Wallet wallet;

    @Setter
    @Getter
    @Column(name = "BETS")
    @OneToMany(mappedBy = "user")
    private ArrayList<Bet> bets;

}