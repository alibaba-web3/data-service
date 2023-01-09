package com.web3.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Author: smy
 * @Date: 2023/1/6 5:35 PM
 */
@Configuration
@MapperScan(basePackages = {"com.web3.dal.data.mapper"}, sqlSessionFactoryRef = "sqlSessionFactoryData")
public class DataDbConfig {

    @Bean(name = "dataDataSource")
    @ConfigurationProperties(prefix = "data")
    public DataSource defaultDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "sqlSessionFactoryData")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
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
