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
    @Setter
    @Getter
    @Column(name = "ID")
    private long id;

    @Setter
    @Getter
    @Column(name = "MONEY")
    private double money;

    @Setter
    @Getter
    @PrimaryKeyJoinColumn(name = "USER")
    @OneToOne(mappedBy = "wallet")
    private User user;

}
