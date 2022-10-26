package org.example.database.springdata.mongodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.example.database.springdata.mongodb.converter.LocalDateToStringConverter;
import org.example.database.springdata.mongodb.converter.StringToLocalDateConverter;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Configuration
@Profile({"springData & mongoDB"})
@PropertySource("classpath:mongodatabase.properties")
@EnableMongoRepositories
public class SpringDataMongoConfig extends AbstractMongoClientConfiguration {

    private final Environment environment;

    public SpringDataMongoConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    @NonNull
    protected MongoClientSettings mongoClientSettings() {

        MongoClientSettings.Builder builder = MongoClientSettings.builder();

        String url = "url";
        builder.applyConnectionString(new ConnectionString(Objects.requireNonNull(environment.getProperty(url))));
        configureClientSettings(builder);
        return builder.build();
    }


    @Bean
    public BeanPostProcessor persistenceTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }


    @Override
    @NonNull
    protected String getDatabaseName() {
        return "uat";
    }

    @Bean
    @Override
    @NonNull
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<>();
        converterList.add(new StringToLocalDateConverter());
        converterList.add(new LocalDateToStringConverter());
        return new MongoCustomConversions(converterList);
    }
}
