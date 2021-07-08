package com.ssh.sustain.config;


import com.ssh.sustain.domain.Role;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.log4j.Log4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Log4j
@Configuration
@ComponentScan(basePackages = "com.ssh.sustain",
        excludeFilters = @ComponentScan.Filter({
                Controller.class,
                RestController.class
        }))
@MapperScan(basePackages = "com.ssh.sustain.mapper")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class RootConfig {

    /**
     * <h4>주의 사항</h4>
     * <p>Annotation Configuration의 경우 빈이 알파벳 순서대로 로딩되기 때문에 순서 조절이 필수적임.</p>
     */

    @Resource
    private Environment env;

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(env.getProperty("db.driverClassName"));
        hikariConfig.setJdbcUrl(env.getProperty("db.jdbcUrl"));
        hikariConfig.setUsername(env.getProperty("db.username"));
        hikariConfig.setPassword(env.getProperty("db.password"));

        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public SqlSessionFactory sessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        sqlSessionFactoryBean.setTypeHandlers(new TypeHandler[]{
                new Role.TypeHandler()
        });

        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
