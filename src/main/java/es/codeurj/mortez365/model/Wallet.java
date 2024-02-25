package es.codeurj.mortez365.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;

@Entity
@Data
@Table(name = "WALLET")
public class Wallet {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long id;

    @Column(name = "MONEY")
    private double money;

    @PrimaryKeyJoinColumn(name = "USER")
    @OneToOne(mappedBy = "wallet")
    private User user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
