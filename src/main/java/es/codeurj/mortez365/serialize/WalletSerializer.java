package es.codeurj.mortez365.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import es.codeurj.mortez365.model.Comment;
import es.codeurj.mortez365.model.Wallet;

import java.io.IOException;

public class WalletSerializer extends JsonSerializer<Wallet> {
    @Override
    public void serialize(Wallet wallet, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(wallet.toString());
    }
}
