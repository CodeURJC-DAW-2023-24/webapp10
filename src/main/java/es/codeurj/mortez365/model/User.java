package es.codeurj.mortez365.model;

import java.util.Date;
import java.util.List;

import jakarta.annotation.Generated;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
@Entity

public class User {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Long id;
    private String name;
    private String firstsurname;
    private String secondsurname;
    private String email;
    private Date birthdate;
    private String nationality;
    private String dni;
    private String adress;
    private String postcode;
    private String telphone;
    private String username;
    private String password;
    private boolean admin;
   
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    public User(String name, String firstsurname, String secondsurname, String email, Date birthdate, String nationality, String dni, String username, String password, boolean admin, String adress, String postcode, String telphone, List<String> roles) {
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

    
}