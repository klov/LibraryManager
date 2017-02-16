package com.ivc.libraryweb.integration.config;

import com.ivc.libraryweb.config.JPAConfig;
import java.util.Properties;
import javax.sql.DataSource;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@Import(JPAConfig.class)
public class TestConfig {

    @Bean
    @Profile("test")
    public DataSource dataSource() {
        DataSource ds = null;
        ds = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
//                .addScript("classpath:db.sql").build();
        System.out.println("create data source");
        return ds;
    }

    @Bean(name = "databaseTester")
    public DataSourceDatabaseTester  dataSourceDatabaseTester() throws Exception {
        DataSourceDatabaseTester databaseTester
                = new DataSourceDatabaseTester(dataSource());
        return databaseTester;
    }

    @Bean
    @Profile("test")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource ds) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(ds);
        em.setPackagesToScan(new String[]{"com.ivc.libraryweb.entities"});
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        System.out.println("create entity manager");
        return em;
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create");
        return properties;
    }
    
}
