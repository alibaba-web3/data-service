package com.web3.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Author: smy
 * @Date: 2023/1/6 5:04 PM
 */
@Configuration
@MapperScan(basePackages = {"com.web3.dal.meta.mapper"}, sqlSessionFactoryRef = "sqlSessionFactoryMeta")
public class MetaDbConfig {

    @Value(value = "${meta.jdbcUrl}")
    private String jdbcUrl;

    @Value(value = "${meta.username}")
    private String username;

    @Value(value = "${meta.password}")
    private String password;

    @Primary
    @Bean(name = "metaDataSource")
    @ConfigurationProperties(prefix = "meta")
    public DataSource defaultDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url(jdbcUrl);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }

    @Bean(name = "sqlSessionFactoryMeta")
    @Primary
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "transactionManagerMeta")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("metaDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionTemplateMeta")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactoryDefault) {
        return new SqlSessionTemplate(sqlSessionFactoryDefault);
    }

}
