package es.codeurj.mortez365.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "TRANSACTION")
public class Transaction {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long id;

    @Column(name = "AMOUNT")
    private double amount;

    // Sentido de la operacion, positivo o negativo
    @Column(name = "SENSE")
    private char sense;

    @PrimaryKeyJoinColumn(name = "WALLET")
    @OneToOne
    private Wallet wallet;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public char getSense() {
        return sense;
    }

    public void setSense(char sense) {
        this.sense = sense;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}