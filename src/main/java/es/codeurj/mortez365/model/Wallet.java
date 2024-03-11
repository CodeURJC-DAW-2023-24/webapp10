package es.codeurj.mortez365.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


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

    @Column(name = "MONEY")
    @Setter
    @Getter
    private double money;

    @PrimaryKeyJoinColumn(name = "USER")
    @OneToOne(mappedBy = "wallet")
    @Setter
    @Getter
    private User user;

}
