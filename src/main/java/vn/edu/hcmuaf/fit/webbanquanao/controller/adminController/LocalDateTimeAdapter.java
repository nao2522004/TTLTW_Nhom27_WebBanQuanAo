package vn.edu.hcmuaf.fit.webbanquanao.controller.adminController;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    // ISO-8601 với múi giờ
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            // Format LocalDateTime sang ISO-8601
            out.value(value.format(formatter));
        }
    }

    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
        String value = in.nextString();

        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            // Parse ISO-8601 thành LocalDateTime
            return LocalDateTime.parse(value, formatter);
        } catch (DateTimeParseException e) {
            throw new IOException("Invalid ISO-8601 date format: " + value, e);
        }
    }
}
