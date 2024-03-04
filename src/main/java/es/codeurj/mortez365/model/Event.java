package es.codeurj.mortez365.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

import java.util.Random;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Data
@Table(name = "EVENT")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    @SequenceGenerator(name="id_generator", sequenceName = "my_sequence", initialValue = 0)
    @Setter
    @Getter
    @Column(name = "ID")
    private long id;

    @Setter
    @Getter
    @JoinColumn(name = "NAME")
    private String name;

    @Setter
    @Getter
    @Column(name = "DESCRIPTION")
    private String description;

    @Setter
    @Getter
    @Column(name = "WINNER_TEAM")
    private String winner_team;

    @Setter
    @Getter
    @Column(name = "LOSER_TEAM")
    private String loser_team;

    @Setter
    @Getter
    @Column(name = "MARKER")
    private String marker;

    // STRING O ENTIDAD A PARTE ????
    @Setter
    @Getter
    @JoinColumn(name = "CHAMPIONSHIP")
    //@ManyToOne
    private String championship;

    @Setter
    @Getter
    @JoinColumn(name = "IMAGE")
    private String image;

    @Setter
    @Getter
    @JoinColumn(name = "SPORT")
    private String sport;

    @Setter
    @Getter
    @JoinColumn(name = "FEE")
    private Double fee;

   
    public Event(String name, String image, String championship, String sport) {
        super();
        this.name = name;
        this.image = image;
        this.championship = championship;
        this.sport = sport;
        this.fee = generateFee();
    }

    private Double generateFee() {
        Random random = new Random();
        double randomFee = 1.5 + (random.nextDouble() * 1);
        return Math.round(randomFee * 100.0)/ 100.0;
    }

    public Event() {

    }

    public void setImage(byte[] bs) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setImage'");
    }

    public String getChampionship() {
        return championship;
    }
    public void setChampionship(String championship) {
        this.championship = championship;
    }
    public String getSport() {
        return sport;
    }
    public void setSport(String sport) {
        this.sport = sport;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
