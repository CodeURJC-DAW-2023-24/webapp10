package es.codeurj.mortez365.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;

@Entity
@Data
@Table(name = "CHAMPIONSHIP")
public class Championship {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "EVENTS")
    @OneToMany(mappedBy = "championship", cascade = CascadeType.ALL)
    private ArrayList<Event> events;
}
