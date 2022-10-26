package org.example.database.springjdbc;

import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


@Configuration
@ComponentScan("org.example.database")
@PropertySource("classpath:database.properties")
@Profile("springJDBC")
public class SpringJdbcConfig {

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
