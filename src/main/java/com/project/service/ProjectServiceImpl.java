package com.project.service;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;

import com.project.model.Projekt;
import com.project.model.Zadanie;
import com.project.repository.ProjectRepository;
import com.project.repository.ZadanieRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProjectServiceImpl implements ProjectService {
	private final ProjectRepository projectRepository;
	private final ZadanieRepository zadanieRepository;
	private final TransactionalOperator transactionalOperator;
	private final R2dbcEntityTemplate entityTemplate;

	public ProjectServiceImpl(ProjectRepository projectRepository, ZadanieRepository zadanieRepository, TransactionalOperator transactionalOperator, R2dbcEntityTemplate entityTemplate) {
		this.projectRepository = projectRepository;
		this.zadanieRepository = zadanieRepository;
		this.transactionalOperator = transactionalOperator;
		this.entityTemplate = entityTemplate;
	}

	@Override
	public Flux<Projekt> findAll() {
		return projectRepository.findAll()
				.delayElements(java.time.Duration.ofSeconds(2));//testy
	}
	
	@Override
	public Flux<Projekt> findByNazwa(String nazwa){
		//TODO
		return projectRepository.findByNazwaContains(nazwa);
	}
	
	@Override
	public Mono<Projekt> findById(final Integer projektId) {
		//TODO
		return projectRepository.findById(projektId);
	}

	@Override
	public Mono<Projekt> create(final Projekt projekt) {
		//TODO
		return projectRepository.save(projekt);
	}
	
	@Override
	public Mono<Projekt> update(final Projekt projekt) {
		//TODO
		return projectRepository
				.findById(projekt.getProjektId())
				.flatMap(p -> {
					p.setNazwa(projekt.getNazwa());
					p.setOpis(projekt.getOpis());
					p.setDataOddania(projekt.getDataOddania());
					p.setDataczasModyfikacji(LocalDateTime.now());
					return projectRepository.save(p);
				});//.switchIfEmpty(..);
	}
	
	@Override
	public Mono<Void> delete(final Integer projektId) {
		//TODO
		return projectRepository.deleteById(projektId);
	}
	
	@Override
	public Mono<Void> deleteAll() {
		//TODO
		return projectRepository.deleteAll();
	}
	
	@Override
	public Mono<Void> deleteProjectWithTasks(final Integer projektId) {
		//TODO
		return entityTemplate.getDatabaseClient()
				.sql("DELETE FROM zadanie WHERE projekt_id = :id")
				.bind("id", projektId)
				.fetch().rowsUpdated()
				.then(entityTemplate.getDatabaseClient()
						.sql("DELETE FROM projekt WHERE projekt_id = :id")
						.bind("id", projektId)
						.fetch().rowsUpdated())
				.then()
				.as(transactionalOperator::transactional);
	}

	@Override
	public Mono<Void> addProjectWithTasks(final Projekt projekt, final List<Zadanie> zadania) {
		Mono<Void> result = projectRepository
				.save(projekt)
				.thenMany(Flux.fromIterable(zadania)
						.map(z -> {
							z.setProjektId(projekt.getProjektId());
							return zadanieRepository.save(z);
						}))
				.then();
		return result;
	}
	
}
