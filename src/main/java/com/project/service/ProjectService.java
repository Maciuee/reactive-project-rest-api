package com.project.service;

import java.util.List;

import com.project.model.Projekt;
import com.project.model.Zadanie;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProjectService {

	Flux<Projekt> findAll();
	
	Flux<Projekt> findByNazwa(String nazwa);
	
	Mono<Projekt> findById(Integer projektId);

	Mono<Projekt> create(Projekt projekt);
	
	Mono<Void> addProjectWithTasks(Projekt projekt, List<Zadanie> zadanie);
	
	Mono<Projekt> update(Projekt projekt);

	Mono<Void> delete(Integer projektId);
	
	Mono<Void> deleteAll();
	
	Mono<Void> deleteProjectWithTasks(Integer projektId);
	
}