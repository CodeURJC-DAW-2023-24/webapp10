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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @Setter
    @Getter
    @JoinColumn(name = "CHAMPIONSHIP")
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

    @Getter
    @Setter
    @JoinColumn(name = "FINISHED")
    private Boolean finished;

    @Getter
    @Setter
    @JoinColumn(name = "DEADLINE")
    private Date deadline;


    public Event(String name, String image, String championship, String sport, Date deadline) {
        super();


        this.name = name;
        this.image = image;
        this.championship = championship;
        this.sport = sport;
        this.fee = generateFee();
        this.finished = false;
        this.deadline = deadline;

    }

    private Double generateFee() {
        Random random = new Random();
        double randomFee = 1.5 + (random.nextDouble() * 1);
        return Math.round(randomFee * 100.0)/ 100.0;
    }

    public Event() {
    }

    
}
