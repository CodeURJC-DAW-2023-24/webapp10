package es.codeurj.mortez365.DTO;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Data
@Table(name = "USER")
public class UserDataDTO {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    @Getter
    @Column(name = "ID")
    private long id;

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
    @PrimaryKeyJoinColumn(name = "USERNAME")
    private String username;



    public UserDataDTO(String name, String firstsurname, String secondsurname, String email, Date birthdate, String nationality, String dni, String username, String password, boolean admin, String adress, String postcode, String telphone,List <String> roles) {
        super();
        this.name = name;
        this.firstsurname = firstsurname;
        this.secondsurname = secondsurname;
        this.email = email;
        this.username = username;
    }

    public UserDataDTO() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public UserDataDTO(String username, String password, List<String> roles) {
        this.username = username;
    }
}



