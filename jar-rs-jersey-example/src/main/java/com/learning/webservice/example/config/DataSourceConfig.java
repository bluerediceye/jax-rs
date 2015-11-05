package com.learning.webservice.example.config;

import com.learning.webservice.example.config.mode.util.ModeUtils;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.h2.tools.Server;
import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.annotation.PostConstruct;
import java.beans.PropertyVetoException;
import java.sql.SQLException;

/**
 * Created by Ming.Li on 05/11/2015.
 *
 * @author Ming.Li
 */
@Configuration
@DependsOn("initApplicationMode")
@PropertySources(
        {
                @PropertySource("classpath:database.properties"),
                @PropertySource("classpath:c3p0.properties")
        }
)
public class DataSourceConfig {

    @Autowired
    private Environment environment;

    @Bean
    public ComboPooledDataSource pooledDataSources() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl("jdbc:h2:mem:testDB");
        dataSource.setDriverClass("org.h2.Driver");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public EmbeddedDatabase h2DataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
                .setName("testDB")
                .setType(EmbeddedDatabaseType.H2)
                .addScript("scripts/create-db.sql")
                .addScript("scripts/insert-data.sql")
                .build();
    }

    @Bean
    public EmbeddedDatabase derbyDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
                .setName("testDB")
                .setType(EmbeddedDatabaseType.DERBY)
                .addScript("scripts/create-db.sql")
                .addScript("scripts/insert-data.sql")
                .build();
    }

    @Bean
    public EmbeddedDatabase hsqlDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
                .setName("testDB")
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("scripts/create-db.sql")
                .addScript("scripts/insert-data.sql")
                .build();
    }

    @Bean
    @Profile("WEBAPP")
    public Server h2WebServer() throws SQLException {
        return Server.createWebServer("-web","-webAllowOthers", "-webPort", "8088", "-browser").start();
    }

    @Bean
    @Profile("WEBAPP")
    public Server h2TcpServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers","-tcpPort", "9099").start();
    }

    @PostConstruct
    public void startDatabaseManager() throws SQLException {
        if (ModeUtils.isStandalone()) {
            //hsqldb
            //DatabaseManagerSwing.main(new String[] { "--url", "jdbc:hsqldb:mem:testdb", "--user", "sa", "--password", "" });

            //derby
            //DatabaseManagerSwing.main(new String[] { "--url", "jdbc:derby:memory:testdb", "--user", "", "--password", "" });

            //h2
            DatabaseManagerSwing.main(new String[]{"--url", "jdbc:h2:mem:testDB", "--user", "sa", "--password", ""});
        }
    }
}