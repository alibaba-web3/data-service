package com.web3.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

/**
 * @Author: smy
 * @Date: 2023/1/6 5:35 PM
 */
@Configuration
@MapperScan(basePackages = {"com.web3.dal.data.mapper"}, sqlSessionFactoryRef = "sqlSessionFactoryData")
public class DataDbConfig {

    @Value(value = "${data.jdbcUrl}")
    private String jdbcUrl;

    @Value(value = "${data.username}")
    private String username;

    @Value(value = "${data.password}")
    private String password;

    @Value(value = "${meta.driver-class-name}")
    private String driverClassName;

    @Bean(name = "dataDataSource")
    @ConfigurationProperties(prefix = "data")
    public DataSource defaultDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName);
        dataSourceBuilder.url(jdbcUrl);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }

    @Bean(name = "dataDataSourceInitializer")
    @ConditionalOnProperty(name = "datasource.initialize", havingValue="true")
    public DataSourceInitializer dataSourceInitializer1(@Qualifier("dataDataSource")DataSource datasource) {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("script/data_schema.sql"));
        //resourceDatabasePopulator.addScript(new ClassPathResource("data-h22.sql"));
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(datasource);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }

    @Bean(name = "sqlSessionFactoryData")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "transactionManagerData")
    public DataSourceTransactionManager transactionManager(@Qualifier("dataDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionTemplateData")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactoryData") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
