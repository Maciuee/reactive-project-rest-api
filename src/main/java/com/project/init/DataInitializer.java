package com.project.init;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.project.model.Projekt;
import com.project.service.ProjectService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@Component
public class DataInitializer {
	private final ProjectService projectService;
		
	public DataInitializer(ProjectService projectService) {
		this.projectService = projectService;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void init() {
		
		final var saveProjects = Flux.just("Projekt 1", "Projekt 2", "Projekt 3", "Projekt 4", "Projekt 5")
				.map(prj -> new Projekt(prj, prj, LocalDateTime.now(), LocalDate.of(2022, 4, 1)))
				.flatMap(projectService::create);
		this.projectService
			.deleteAll()
			.thenMany(saveProjects)
			.thenMany(projectService.findAll())
		.subscribe(p ->  log.info(p.getNazwa()));
		
	}
	
}
