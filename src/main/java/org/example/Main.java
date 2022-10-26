package org.example;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;


public class Main {


    public static void main(String[] args) {
        checkProfiles();
        try (ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            context.getBean(App.class).run();
        }
    }

    private static void checkProfiles() {
        List<String> databases = Arrays.asList("postgres", "h2");
        List<String> databaseAccess = Arrays.asList("springData", "springJDBC", "hibernate");
        String validProfiles = "\n\tValid Profiles Are: \n\t\t" + databases + " with " + databaseAccess + " or [mongoDB] with [springData]";

        String profilesString = getProfilesString();

        if (profilesString == null) {
            throw new IllegalArgumentException("Please select any Profiles." + validProfiles);
        }

        List<String> profiles = Arrays.asList(profilesString.split(","));

        if (profiles.size() != 2) {
            throw new IllegalArgumentException("Not the Right amount of Profiles chosen. Please select 2." + validProfiles);
        } else if (profiles.contains("mongoDB")) {
            if (!profiles.contains("springData")) {
                throw new IllegalArgumentException("[mongoDB] is only available with [springData]");
            }
        } else if (profiles.stream().noneMatch(databases::contains) || profiles.stream().noneMatch(databaseAccess::contains)) {
            throw new IllegalArgumentException("Please select a combination form a 'Database' and a 'Database connection'." + validProfiles);
        }

    }

    private static String getProfilesString() {
        return System.getProperties().getProperty("spring.profiles.active");
    }

}