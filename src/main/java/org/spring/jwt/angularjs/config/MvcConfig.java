package org.spring.jwt.angularjs.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class MvcConfig {

    @Bean
    public DozerBeanMapper getMapper() {
        return new DozerBeanMapper();
    }
}
