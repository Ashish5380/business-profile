package com.personal.businessprofile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableMongoAuditing
@EnableScheduling
@ComponentScan(basePackages = {"com.personal"})
public class BusinessProfileApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessProfileApplication.class, args);
    }

}
