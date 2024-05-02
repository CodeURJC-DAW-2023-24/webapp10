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

    @PrimaryKeyJoinColumn(name = "USERNAME")
    private String username;



    public UserDataDTO(Long id, String name, String firstsurname, String secondsurname, String email, String username) {
        super();
        this.name = name;
        this.firstsurname = firstsurname;
        this.secondsurname = secondsurname;
        this.email = email;
        this.username = username;
        this.id = id;
    }

    public UserDataDTO() {
        super();
    }


    public UserDataDTO(String username, String password, List<String> roles) {
        this.username = username;
    }


}



