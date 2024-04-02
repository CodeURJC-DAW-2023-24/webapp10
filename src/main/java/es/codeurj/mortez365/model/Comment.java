package es.codeurj.mortez365.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    @Getter
    @Column(name = "ID")
    private long id;

    @Getter
    @Setter
    @JoinColumn(name = "AUTHOR")
    @ManyToOne
    private User user;

    @Getter
    @Setter
    @Column(name = "CONTENT")
    private String content;

    @Getter
    @Setter
    @JoinColumn(name = "EVENT")
    @ManyToOne
    private Event event;

    public Comment() {
        super();
    }

}
