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


import jakarta.persistence.*;

import java.util.Random;



@Entity
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    @SequenceGenerator(name="id_generator", sequenceName = "my_sequence", initialValue = 0)
    private Long id;
    @ManyToOne
    private Event event;
    private Double money;
    private Result result;
    private Double winning_amount;
    private Double benefit;

    public Bet() {
    }

    public Bet(Event event, Double money, Result result, Double winning_amount, Double benefit) {
        super();
        this.event = event;
        this.money = money;
        this.result = result;
        this.winning_amount = winning_amount;
        this.benefit = benefit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Double getWinning_amount() {
        return winning_amount;
    }

    public void setWinning_amount(Double winning_amount) {
        this.winning_amount = winning_amount;
    }

    public Double getBenefit() {
        return benefit;
    }

    public void setBenefit(Double benefit) {
        this.benefit = benefit;
    }
}
