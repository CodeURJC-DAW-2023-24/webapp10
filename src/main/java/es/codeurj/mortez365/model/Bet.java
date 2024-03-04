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
    @PrimaryKeyJoinColumn(name = "ID")
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
    @OneToOne
    @JoinColumn(name = "EVENT")
    private Event event;

    @Setter
    @Getter
    @Column(name = "RESULT")
    private Result result;


    @Setter
    @Getter
    @JoinColumn(name = "USER")
    @ManyToOne
    private User user;

    public Bet() {
    }

    public Bet(Event event, double bet_amount, Result result, double winning_amount, double profit, User user) {
        super();
        this.bet_amount = bet_amount;
        this.result = result;
        this.winning_amount = winning_amount;
        this.profit = profit;
        this.event = event;
        this.user = user;
    }
}