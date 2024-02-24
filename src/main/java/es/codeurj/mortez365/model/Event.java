package es.codeurj.mortez365.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Data
@Table(name = "EVENT")
public class Event {

    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "WINNER_TEAM")
    private String winner_team;

    @Column(name = "LOSER_TEAM")
    private String loser_team;

    @Column(name = "MARKER")
    private String marker;

    @JoinColumn(name = "CHAMPIONSHIP")
    @ManyToOne
    private Championship championship;

}
