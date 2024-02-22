package es.codeurj.mortez365.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Data
@Table(name = "BET")
public class Bet {

    @Id
    @Column(name = "ID")
    private long id;

    // Igual sobra la descripcion
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "BET_AMOUNT")
    private double bet_amount;

    @Column(name = "WINNING_AMOUNT")
    private double winning_amount;

    @Column(name = "PROFIT")
    private double profit;

    @Column(name = "EVENT")
    @OneToOne
    private Event event;

    @Column(name = "USER")
    @ManyToOne
    private User user;

}

