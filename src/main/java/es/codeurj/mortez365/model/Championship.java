package es.codeurj.mortez365.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Entity
@Data
@Table(name = "CHAMPIONSHIP")
public class Championship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    @Getter
    @Column(name = "ID")
    private String id;

    @Setter
    @Getter
    @Column(name = "DESCRIPTION")
    private String description;

    @Setter
    @Getter
    @Column(name = "EVENTS")
    /*@OneToMany(mappedBy = "championship", cascade = CascadeType.ALL)
    private ArrayList<Event> events;*/
    private String events;

}
