 package com.sca.ShoppingCartAppMind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.sca.ShoppingCartAppMind")
@EnableAutoConfiguration(exclude=HibernateJpaAutoConfiguration.class)
@EnableEurekaClient
public class ShoppingCartAppMindApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingCartAppMindApplication.class, args);
	}
	//Sidharth das

}
