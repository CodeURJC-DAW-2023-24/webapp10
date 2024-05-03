package es.codeurj.mortez365.DTO;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class UserDataDTO {
    
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "FIRSTSURNAME")
    private String firstsurname;

    @Column(name = "SECONDSURNAME")
    private String secondsurname;

    @Column(name = "EMAIL")
    private String email;

    @Column(name= "ADRRESS")
    private String address;

    @Column(name = "TELEPHONE")
    private String telephone;

    @Column(name = "BIRTHDATE")
    private Date birthdate;

    @Column( name = "NATIONALITY")
    private String nationality;

    @Column(name = "DNI")
    private String dni;

    @Column(name = "ADRESS")
    private String adress;

    @Column(name = "POSTCODE")
    private String postcode;

    @PrimaryKeyJoinColumn(name = "USERNAME")
    private String username;



    public UserDataDTO(Long id, String name, String firstsurname, String secondsurname, String email, String username, String adress, String postcode, String telephone, String dni , Date birthdate) {
        super();
        this.name = name;
        this.firstsurname = firstsurname;
        this.secondsurname = secondsurname;
        this.email = email;
        this.username = username;
        this.adress = adress;
        this.postcode = postcode;
        this.telephone = telephone;
        this.dni = dni;
        this.birthdate = birthdate;
        this.id = id;
    }

    public UserDataDTO() {
        super();
    }


    public UserDataDTO(String username, String password, List<String> roles) {
        this.username = username;
    }


}



