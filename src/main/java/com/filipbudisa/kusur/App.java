package com.filipbudisa.kusur;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;

@EnableJpaRepositories("com.filipbudisa.kusur.repository")
@EntityScan("com.filipbudisa.kusur.model")
@SpringBootApplication
public class App {

	public static void main(String[] args){
		try{
			new Logger();
		}catch(IOException e){
			e.printStackTrace();
			return;
		}

		SpringApplication.run(App.class, args);
	}
}
