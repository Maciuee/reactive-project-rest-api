package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReactiveProjectRestApplication {
	//UWAGA! Przed uruchomieniem utworz (np. pgAdmin) baze 'projects'
	public static void main(String[] args) {
		SpringApplication.run(ReactiveProjectRestApplication.class, args);
	}
	
}