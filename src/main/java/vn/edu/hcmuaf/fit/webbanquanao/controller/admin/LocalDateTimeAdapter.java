package vn.edu.hcmuaf.fit.webbanquanao.controller.admin;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.format(formatter));
        }
    }

    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
        String value = in.nextString();

        // Kiểm tra chuỗi có phải null hoặc rỗng không
        if (value == null || value.isEmpty()) {
            return null; // Trả về null nếu giá trị không hợp lệ
        }

        try {
            return LocalDateTime.parse(value, formatter);
        } catch (DateTimeParseException e) {
            // Xử lý ngoại lệ nếu định dạng không hợp lệ
            throw new IOException("Invalid date format: " + value, e);
        }
    }
}
