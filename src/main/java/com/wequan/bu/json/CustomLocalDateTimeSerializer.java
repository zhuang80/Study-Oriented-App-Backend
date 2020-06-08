package com.wequan.bu.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Zhaochao Huang
 */
public class CustomLocalDateTimeSerializer extends StdSerializer<LocalDateTime> {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd@HH:mm:ss");

    public CustomLocalDateTimeSerializer() {
        this(null);
    }

    public CustomLocalDateTimeSerializer(Class<LocalDateTime> t){
        super(t);
    }
    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(formatter.format(localDateTime));
    }
}
