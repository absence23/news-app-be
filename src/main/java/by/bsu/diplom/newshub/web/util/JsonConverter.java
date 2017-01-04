package by.bsu.diplom.newshub.web.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@JsonComponent
public class JsonConverter {

    public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            long time = jsonParser.getValueAsLong();
            if (time == 0) {
                return LocalDateTime.MIN;
            }
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
        }
    }

    public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
        @Override
        public void serialize(LocalDateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (dateTime != null) {
                jsonGenerator.writeNumber(dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            } else jsonGenerator.writeNull();
        }
    }
}
