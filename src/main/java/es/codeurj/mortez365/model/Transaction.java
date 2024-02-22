package es.codeurj.mortez365.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "TRANSACTION")
public class Transaction {

    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "AMOUNT")
    private double amount;

    // Sentido de la operacion, positivo o negativo
    @Column(name = "SENSE")
    private char sense;

    @Column(name = "WALLET")
    @OneToOne
    private Wallet wallet;
}