package es.codeurj.mortez365.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Data
@Table(name = "EVENT")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    @Getter
    @Column(name = "ID")
    private long id;

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

}
