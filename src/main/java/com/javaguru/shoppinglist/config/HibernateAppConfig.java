package com.javaguru.shoppinglist.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.javaguru.shoppinglist")
@PropertySource("classpath:application.properties")
//@Profile({"hibernate"})
public class HibernateAppConfig {

    @Bean
    @Profile({"hibernate"})
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @Profile({"hibernate"})
    public DataSource dataSource(
            @Value("${jdbc.url}")
                    String jdbcUrl,
            @Value("${driverClass}")
                    String driverClass,
            @Value("${database.user.name}")
                    String userName,
            @Value("${database.user.password}")
                    String userPassword
    ) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setDriverClassName(driverClass);
        dataSource.setUsername(userName);
        dataSource.setPassword(userPassword);
        return dataSource;
    }

    @Bean
    @Profile({"hibernate"})
    public Properties hibernateProperties(
            @Value("${hibernate.dialect}") String dialect,
            @Value("${hibernate.show_sql}") boolean showSql,
            @Value("${hibernate.format_sql}") boolean formatSql,
            @Value("${hibernate.hbm2ddl.auto}") String hbm2ddl) {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", dialect);
        properties.put("hibernate.show_sql", showSql);
        properties.put("hibernate.format_sql", formatSql);
        properties.put("hibernate.hbm2ddl.auto", hbm2ddl);

        return properties;
    }

    @Bean
    @Profile({"hibernate"})
    public SessionFactory sessionFactory(
            DataSource dataSource,
            @Value("com.javaguru.shoppinglist.entity") String packageToScan,
            Properties hibernateProperties
    ) throws Exception {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource);
        localSessionFactoryBean.setPackagesToScan(packageToScan);
        localSessionFactoryBean.setHibernateProperties(hibernateProperties);
        localSessionFactoryBean.afterPropertiesSet();
        return localSessionFactoryBean.getObject();
    }

    @Bean
    @Profile({"hibernate"})
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }
}
