package com.project.client;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.web.reactive.function.client.WebClient;

import com.project.model.Projekt;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class ReactiveWebClientApp {

	private final WebClient webClient;

	public ReactiveWebClientApp(WebClient webClient) {
		this.webClient = webClient;
	}
	
	public static void main(String[] args) {
		new ReactiveWebClientApp(WebClient.create("http://localhost:8081")).test();
	}
	
	private void test() {
//		create(new Projekt("Nazwa testowa", "Opis testowy", LocalDateTime.now(), LocalDate.of(2022, 4, 1)))
//		 .doOnNext(p -> {
//			log.info("Id: {}, Nazwa: {}", p.getProjektId(), p.getNazwa());})
//		 .block();
		
		findAll()
		 .doOnNext(p -> {
			log.info("Id: {}, Nazwa: {}", p.getProjektId(), p.getNazwa());})
		 .blockLast();
	}
	//TODO Ponizsze metody przeniesc do nowego serwisu
	public Flux<Projekt> findAll() {
		return webClient.get()
				.uri("/projects")
				.retrieve()
				.bodyToFlux(Projekt.class);
	}
	
	public Mono<Projekt> create(Projekt projekt) {
		return webClient.post()
				.uri("/projects")
				.body(Mono.just(projekt), Projekt.class)
				.retrieve()
				.bodyToMono(Projekt.class);
	}
	
	public Mono<Projekt> findById(Integer projektId){
		return webClient.get()
				.uri("/projects/" + projektId)
				.retrieve()
				.bodyToMono(Projekt.class);
	}

	public Mono<Projekt> update(Projekt projekt) {
		return webClient.put()
				.uri("/projects/" + projekt.getProjektId())
				.body(Mono.just(projekt), Projekt.class)
				.retrieve()
				.bodyToMono(Projekt.class);
	}

	public Mono<Void> delete(Integer projektId) {
		return webClient.delete()
				.uri("/projects/" + projektId)
				.retrieve()
				.bodyToMono(Void.class);
	}
	
}
