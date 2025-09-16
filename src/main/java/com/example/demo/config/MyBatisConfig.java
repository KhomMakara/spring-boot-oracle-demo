package com.example.demo.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan("com.example.demo.mapper")
public class MyBatisConfig {

    private static final Logger logger = LoggerFactory.getLogger(MyBatisConfig.class);

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        // Set the location of MyBatis mapper XML files
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath*:mybatis/mapper/*.xml");

        // Log the resources to see if they contain the correct file path
        for (Resource resource : resources) {
            logger.info("Found mapper XML file: {}", resource.getFilename());
        }

        sessionFactory.setMapperLocations(resources);

        return sessionFactory.getObject();
    }
}


