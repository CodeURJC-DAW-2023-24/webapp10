package es.codeurj.mortez365.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "BET")
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    @Getter
    @Column(name = "ID")
    private long id;

    @Setter
    @Getter
    @Column(name = "DESCRIPTION")
    private String description;

    @Setter
    @Getter
    @Column(name = "BET_AMOUNT")
    private double bet_amount;

    @Setter
    @Getter
    @Column(name = "WINNING_AMOUNT")
    private double winning_amount;

    @Setter
    @Getter
    @Column(name = "PROFIT")
    private double profit;

    @Setter
    @Getter
    @PrimaryKeyJoinColumn(name = "EVENT")
    @OneToOne
    private Event event;

    @Setter
    @Getter
    @JoinColumn(name = "USER")
    @ManyToOne
    private User user;

}
