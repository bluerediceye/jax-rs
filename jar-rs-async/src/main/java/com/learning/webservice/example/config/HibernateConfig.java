package com.learning.webservice.example.config;

import com.learning.webservice.example.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;


/**
 * Created by Ming.Li on 05/11/2015.
 *
 * @author Ming.Li
 */
@Configuration
@Import({DataSourceConfig.class})
public class HibernateConfig {

    @Autowired(required = false)
    private DataSource h2DataSource;

    @Autowired(required = false)
    private DataSource hsqlDataSource;

    @Autowired(required = false)
    private DataSource derbyDataSource;

    @Autowired
    private DataSource pooledDataSources;

    @Bean
    public JpaTransactionManager transactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(pooledDataSources);
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan(Application.class.getPackage().getName());
        factoryBean.setPersistenceXmlLocation("classpath:META-INF/persistence.xml");
        factoryBean.setPersistenceUnitName("persistenceUnit");
        factoryBean.setJtaDataSource(pooledDataSources);
        factoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter());
        factoryBean.setJpaDialect(hibernateJpaDialect());
        return factoryBean;
    }

    @Bean
    public HibernateJpaDialect hibernateJpaDialect(){
        return new HibernateJpaDialect();
    }

    @Bean
    public HibernateJpaVendorAdapter hibernateJpaVendorAdapter(){
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.H2);
        adapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect");
        adapter.setGenerateDdl(false);
        adapter.setShowSql(true);
        return adapter;
    }
}