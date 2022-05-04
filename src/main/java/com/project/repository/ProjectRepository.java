package com.project.repository;

import java.time.LocalDate;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.project.model.Projekt;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProjectRepository extends ReactiveCrudRepository<Projekt, Integer> {
	
	@Query("SELECT * FROM projekt WHERE nazwa LIKE CONCAT('%', :nazwa, '%')")//$1
	Flux<Projekt> findByNazwaContains(String nazwa);
	
	@Modifying
	@Query("UPDATE projekt SET data_oddania = :dataOddania")
	Mono<Integer> setFixedDeadline(LocalDate dataOddania);
}
