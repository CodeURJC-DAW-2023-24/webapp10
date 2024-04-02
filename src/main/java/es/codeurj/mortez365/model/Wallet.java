package es.codeurj.mortez365.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
@Data
@Table(name = "WALLET")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    @Setter
    @Getter
    private long id;

    @Column(name = "CARD_NUMBER")
    @Setter
    @Getter
    private String card_number;

    @Column(name = "EXPIRED_DATE")
    @Setter
    @Getter
    private String expired_date;

    @Column(name = "CVV")
    @Setter
    @Getter
    private String cvv;

    @Column(name = "OWNER")
    @Setter
    @Getter
    private String owner;

    @Column(name = "MONEY")
    @Setter
    @Getter
    private double money;

    @PrimaryKeyJoinColumn(name = "USER")
    @OneToOne(mappedBy = "wallet")
    @Setter
    @Getter
    private User user;

    public Wallet(String card_number, String cvv, String expired_date, User user) {
        this.card_number = card_number;
        this.cvv = cvv;
        this.money = 100;
        this.user = user;
        this.expired_date = expired_date;
        this.owner = user.getName() + " " + user.getFirstsurname();
    }

    public Wallet() {
    }

    public void addMoney(double profit) {
        this.money += profit;
    }
}
