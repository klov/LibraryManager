
package com.ivc.libraryweb.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author Vitaliy Denisov
 */
public class CustomLocalDateTimeSerializer  extends StdSerializer<Date> {
 
    private static SimpleDateFormat formatter = 
      new SimpleDateFormat("dd.MM.yyyy");
 
    public CustomLocalDateTimeSerializer() {
        this(null);
    }
  
    public CustomLocalDateTimeSerializer(Class<Date> t) {
        super(t);
    }
     
    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider arg2)
      throws IOException, JsonProcessingException {
        gen.writeString(formatter.format(value));
    }
}