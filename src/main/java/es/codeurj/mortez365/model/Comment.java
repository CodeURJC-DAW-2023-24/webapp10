package es.codeurj.mortez365.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private User user;

    @Getter
    @Setter
    private String userName;

    @Getter
    @Setter
    @Column(name = "CONTENT")
    private String content;

    @Getter
    @Setter
    @JoinColumn(name = "EVENT")
    @ManyToOne
    @JsonIgnore
    private Event event;

    @Getter
    @Setter
    private String eventName;

    public Comment() {
        super();
    }

    public Comment(User user, String content, Event event) {
        this.user = user;
        this.content = content;
        this.event = event;
    }
}
