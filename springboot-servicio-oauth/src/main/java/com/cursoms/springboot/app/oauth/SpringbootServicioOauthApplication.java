package com.cursoms.springboot.app.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.swing.*;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class SpringbootServicioOauthApplication implements CommandLineRunner {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioOauthApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String password = "12345";
		for (int i= 0;i<4;i++){
			String passwordBCrypt = passwordEncoder.encode(password);
			System.out.println(passwordBCrypt);

		}
		System.out.println(env.getProperty("config.security.oauth.client.id"));
	}
}
