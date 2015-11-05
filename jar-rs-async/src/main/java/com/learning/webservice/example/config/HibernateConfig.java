package com.learning.webservice.example.config;

import com.learning.webservice.example.Application;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;


/**
 * Created by Ming.Li on 05/11/2015.
 *
 * @author Ming.Li
 */
@Configuration
public class HibernateConfig {

    @Bean
    public JpaTransactionManager transactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(dataSource());
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPackagesToScan(Application.class.getPackage().getName());
        factoryBean.setJtaDataSource(dataSource());
        return factoryBean;
    }

    @Bean
    public DataSource dataSource(){
        return null;
    }
}
