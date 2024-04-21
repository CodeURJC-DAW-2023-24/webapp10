package es.codeurj.mortez365.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import es.codeurj.mortez365.serialize.EventSerializer;
import es.codeurj.mortez365.serialize.UserSerializer;
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
    @JsonSerialize(using = UserSerializer.class)
    private User user;



    @Getter
    @Setter
    @Column(name = "CONTENT")
    private String content;

    @Getter
    @Setter
    @JoinColumn(name = "EVENT")
    @ManyToOne
    @JsonSerialize(using = EventSerializer.class)
    private Event event;


    public Comment() {
        super();
    }

    public Comment(User user, String content, Event event) {
        this.user = user;
        this.content = content;
        this.event = event;
    }
}
