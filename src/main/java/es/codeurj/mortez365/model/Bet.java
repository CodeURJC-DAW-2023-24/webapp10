package es.codeurj.mortez365.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "BET")
public class Bet {

    @Id
    @GeneratedValue
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBet_amount() {
        return bet_amount;
    }

    public void setBet_amount(double bet_amount) {
        this.bet_amount = bet_amount;
    }

    public double getWinning_amount() {
        return winning_amount;
    }

    public void setWinning_amount(double winning_amount) {
        this.winning_amount = winning_amount;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

