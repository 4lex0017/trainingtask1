package org.example.database.springdata.mongodb.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@ReadingConverter
public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(@Nullable String source) {
        return LocalDate.parse(Objects.requireNonNull(source), DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
