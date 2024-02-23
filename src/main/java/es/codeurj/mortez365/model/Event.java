package es.codeurj.mortez365.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    @SequenceGenerator(name="id_generator", sequenceName = "my_sequence", initialValue = 0)
    private Long id;
    private String name;
    private String image;
    private String championship;
    private String sport;

    public Event() {
    }

    public Event(String name, String image, String championship, String sport) {
        super();
        this.name = name;
        this.image = image;
        this.championship = championship;
        this.sport = sport;
    }


    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public String getChampionship() {
        return championship;
    }
    
    public void setChampionship(String championship) {
        this.championship = championship;
    }

    
    
}
