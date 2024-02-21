package es.codeurj.mortez365.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String image;
    private String championship;

    public Match() {
    }

    public Match(String name, String image, String championship) {
        super();
        this.name = name;
        this.image = image;
        this.championship = championship;
    }

    
    
}
