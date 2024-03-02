package es.codeurj.mortez365.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Entity
@Data
@Table(name = "USER")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    @Getter
    @Column(name = "ID")
    private long id;

    @Setter
    @Getter
    @PrimaryKeyJoinColumn(name = "WALLET")
    @OneToOne
    private Wallet wallet;

    @Setter
    @Getter
    @Column(name = "BETS")
    @OneToMany(mappedBy = "user")
    private ArrayList<Bet> bets;

    @Setter
    @Getter
    @Column(name = "NAME")
    private String name;

    @Setter
    @Getter
    @Column(name = "FIRSTSURNAME")
    private String firstsurname;

    @Setter
    @Getter
    @Column(name = "SECONDSURNAME")
    private String secondsurname;

    @Setter
    @Getter
    @Column(name = "EMAIL")
    private String email;

    @Setter
    @Getter
    @Column(name = "BIRTHDATE")
    private Date birthdate;

    @Setter
    @Getter
    @Column(name = "NATIONALITY")
    private String nationality;
   
    @Setter
    @Getter
    @Column(name = "DNI")
    private String dni;

    @Setter
    @Getter
    @Column(name = "ADRESS")
    private String adress;

    @Setter
    @Getter
    @Column(name = "POSTCODE")
    private String postcode;

    @Setter
    @Getter
    @Column(name = "TELPHONE")
    private String telphone;

    @Setter
    @Getter
    @Column(name = "USERNAME")
    private String username;

    @Setter
    @Getter
    @Column(name = "PASSWORD")
    private String password;

    @Setter
    @Getter
    @Column(name = "ADMIN")
    private boolean admin;
   

    @Setter
    @Getter
    @Column(name = "ROLE")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    public User(String name, String firstsurname, String secondsurname, String email, Date birthdate, String nationality, String dni, String username, String password, boolean admin, String adress, String postcode, String telphone,List <String> roles) {
        super();
        this.name = name;
        this.firstsurname = firstsurname;
        this.secondsurname = secondsurname;
        this.email = email;
        this.birthdate = birthdate;
        this.nationality = nationality;
        this.dni = dni;
        this.username = username;
        this.password = password;
        this.admin = admin;
        this.adress = adress;
        this.postcode = postcode;
        this.telphone = telphone;
        this.roles = roles;


    }

    public User() {
        super();
    }

   


}

