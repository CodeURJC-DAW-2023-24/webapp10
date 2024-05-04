package es.codeurj.mortez365.serialize;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import es.codeurj.mortez365.model.Event;

import java.io.IOException;

public class EventSerializer extends JsonSerializer<Event> {
    @Override
    public void serialize(Event event, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", event.getName());
        jsonGenerator.writeBooleanField("finished", event.getFinished());
        jsonGenerator.writePOJOField("finalResult", event.getFinalResult());
        jsonGenerator.writeEndObject();
    }
}