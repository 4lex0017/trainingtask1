package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.awt.*;

@Configuration
@ComponentScan("org.example")
public class AppConfig {

    @Bean
    Paint[] paintSequence() {
        return new Color[]{Color.decode("#918F39"),
                Color.decode("#316A82"),
                Color.decode("#318254"),
                Color.decode("#4F3182")};
    }



}
