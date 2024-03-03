package es.codeurj.mortez365.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
@Entity
@Data
@Table(name = "USER")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    @PrimaryKeyJoinColumn(name = "WALLET")
    @OneToOne
    private Wallet wallet;

    @Column(name = "BETS")
    @OneToMany(mappedBy = "user")
    private ArrayList<Bet> bets;

    @Column(name = "NAME")
    private String name;

    @Column(name = "FIRSTSURNAME")
    private String firstsurname;

    @Column(name = "SECONDSURNAME")
    private String secondsurname;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "BIRTHDATE")
    private Date birthdate;

    @Column(name = "NATIONALITY")
    private String nationality;
   
    @Column(name = "DNI")
    private String dni;

    @Column(name = "ADRESS")
    private String adress;

    @Column(name = "POSTCODE")
    private String postcode;

    @Column(name = "TELPHONE")
    private String telphone;


    @Column(name = "USERNAME")
    private String username;


    @Column(name = "PASSWORD")
    private String password;
    
    @Column(name= "MONEY")
    private double money;


    @Column(name = "ADMIN")
    private boolean admin;

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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public ArrayList<Bet> getBets() {
        return bets;
    }

    public void setBets(ArrayList<Bet> bets) {
        this.bets = bets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstsurname() {
        return firstsurname;
    }

    public void setFirstsurname(String firstsurname) {
        this.firstsurname = firstsurname;
    }

    public String getSecondsurname() {
        return secondsurname;
    }

    public void setSecondsurname(String secondsurname) {
        this.secondsurname = secondsurname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}

