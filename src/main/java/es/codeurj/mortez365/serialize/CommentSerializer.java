package es.codeurj.mortez365.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import es.codeurj.mortez365.model.Comment;
import es.codeurj.mortez365.model.Event;

import java.io.IOException;

public class CommentSerializer extends JsonSerializer<Comment>{
    @Override
    public void serialize(Comment comment, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(comment.getContent());
    }
}

