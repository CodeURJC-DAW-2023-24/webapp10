package es.codeurj.mortez365.model;



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

    public Bet() {
    }

    public Bet(Event event, Double money, Result result) {
        super();
        this.event = event;
        this.money = money;
        this.result = result;
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
}
