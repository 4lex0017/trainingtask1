package org.example.database.hibernate;

import org.example.data.FundData;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.Closeable;
import java.io.IOException;
import java.util.Properties;

@Configuration
@Profile("hibernate")
public class MyHibernateConfig implements Closeable {

    private StandardServiceRegistry serviceRegistry;

    @Bean
    public SessionFactory sessionFactory(DataSource dataSource, @Qualifier("properties") Properties properties) {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.setProperties(properties);
        configuration.addAnnotatedClass(FundData.class);
        this.serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(this.serviceRegistry);
    }

    @Bean(name = "properties")
    @Profile("postgres")
    public Properties propertiesPost(DriverManagerDataSource dataSource) {
        Properties properties = new Properties();
        properties.setProperty(Environment.SHOW_SQL, "true");
        properties.setProperty("dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
        properties.setProperty(Environment.DRIVER, "org.postgresql.Driver");
        properties.setProperty(Environment.URL, dataSource.getUrl());
        properties.setProperty(Environment.USER, dataSource.getUsername());
        properties.setProperty(Environment.PASS, dataSource.getPassword());
        properties.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.setProperty(Environment.HBM2DDL_AUTO, "create");
        return properties;
    }

    @Bean(name = "properties")
    @Profile("h2")
    public Properties propertiesH2(DriverManagerDataSource dataSource) {
        Properties properties = new Properties();
        properties.setProperty(Environment.SHOW_SQL, "true");
        properties.setProperty("dialect", "org.hibernate.dialect.H2Dialect");
        properties.setProperty(Environment.DRIVER, "org.h2.Driver");
        properties.setProperty(Environment.URL, dataSource.getUrl());
        properties.setProperty(Environment.USER, dataSource.getUsername());
        properties.setProperty(Environment.PASS, dataSource.getPassword());
        properties.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.setProperty(Environment.HBM2DDL_AUTO, "create");
        return properties;
    }


    @Override
    public void close() throws IOException {
        System.out.println("Close Service Registry");
        serviceRegistry.close();
    }
}
