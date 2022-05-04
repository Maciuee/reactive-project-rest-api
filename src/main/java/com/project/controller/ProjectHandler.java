package com.project.controller;

import java.net.URI;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.project.model.Projekt;
import com.project.service.ProjectService;

import reactor.core.publisher.Mono;

@Component
public class ProjectHandler {
	private final ProjectService projectService;

	public ProjectHandler(ProjectService projectService) {
		this.projectService = projectService;
	}
	
	public Mono<ServerResponse> findAll(ServerRequest request) {
	
		return ServerResponse.ok()
				.contentType(MediaType.TEXT_EVENT_STREAM)
				//.contentType(MediaType.APPLICATION_NDJSON)
				//.contentType(MediaType.APPLICATION_JSON)
				.body(projectService.findAll(), Projekt.class);
	}

	public Mono<ServerResponse> findById(ServerRequest request) {
				return projectService
				.findById(Integer.valueOf(request.pathVariable("id")))
				.flatMap(projekt -> ServerResponse
						.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.bodyValue(projekt))
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	public Mono<ServerResponse> findByName(ServerRequest request) {
		//TODO
		return ServerResponse.ok()
				.contentType(MediaType.TEXT_EVENT_STREAM)
				.body(projectService.findByNazwa(request.queryParam("nazwa").get()), Projekt.class);
	}

	public Mono<ServerResponse> create(ServerRequest request) {
		//TODO
		return request
				.bodyToMono(Projekt.class)
				.flatMap(projectService::create)
				.flatMap(p -> ServerResponse
						.created(URI.create(String.format("projects/%d", p.getProjektId())))
				.build());
    }
	
	public Mono<ServerResponse> update(ServerRequest request) {	
		//TODO
		return request
				.bodyToMono(Projekt.class)
				.flatMap(projectService::update)
				.flatMap(projekt -> ServerResponse.noContent().build())
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	 public Mono<ServerResponse> delete(ServerRequest request) {
		//TODO
		return projectService
				.findById(Integer.valueOf(request.pathVariable("id")))
				.flatMap(projekt -> ServerResponse
						.noContent()
						.build(projectService.delete(projekt.getProjektId())))
						.switchIfEmpty(ServerResponse.notFound().build());
	 }
}
