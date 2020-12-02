package com.personal.common.config;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.personal.common.utils.DateUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author sunpeikai
 * @version LocalDateTimeConvertConfiguration, v0.1 2020/12/1 14:55
 */
@Configuration
public class LocalDateTimeConvertConfiguration {
    @Bean
    public LocalDateTimeSerializer localDateTimeDeserializer() {
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DateUtils.DATE_TIME_PATTERN_19));
    }

    @Bean
    public LocalDateSerializer localDateSerializer(){
        return new LocalDateSerializer(DateTimeFormatter.ofPattern(DateUtils.DATE_PATTERN_10));
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder.serializerByType(LocalDateTime.class, localDateTimeDeserializer())
                .serializerByType(LocalDate.class, localDateSerializer());
    }
}
