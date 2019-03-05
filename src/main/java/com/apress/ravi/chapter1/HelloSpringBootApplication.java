package com.apress.ravi.chapter1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
/**
* @author AkhmadYudhaMahardika
*/

@SpringBootApplication
@ComponentScan(basePackages = { "com.apress.ravi" })
@EnableJpaRepositories(basePackages = { "com.apress.ravi" })
@EntityScan(basePackages = { "com.apress.ravi" })

public class HelloSpringBootApplication {
        public static void main(String[] args) {
                SpringApplication.run(HelloSpringBootApplication.class, args);
        }
}