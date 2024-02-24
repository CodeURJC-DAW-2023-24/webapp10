package es.codeurj.mortez365.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "BET")
public class Bet {

    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "BET_AMOUNT")
    private double bet_amount;

    @Column(name = "WINNING_AMOUNT")
    private double winning_amount;

    @Column(name = "PROFIT")
    private double profit;

    @PrimaryKeyJoinColumn(name = "EVENT")
    @OneToOne
    private Event event;

    @JoinColumn(name = "USER")
    @ManyToOne
    private User user;

}

