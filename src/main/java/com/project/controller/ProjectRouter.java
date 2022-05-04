package com.project.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RequestPredicates.queryParam;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProjectRouter {
	
	@Bean
	public RouterFunction<ServerResponse> projectsRoute(ProjectHandler projectHandler) {
		return RouterFunctions
				.route(GET("/projects"), projectHandler::findAll)
				.andRoute(GET("/projects/{id}"), projectHandler::findById)
				.andRoute(GET("/projects").and(queryParam("nazwa", n -> true)), projectHandler::findByName)
				.andRoute(POST("/projects").and(contentType(APPLICATION_JSON)), projectHandler::create)
				.andRoute(PUT("/projects").and(contentType(APPLICATION_JSON)), projectHandler::update)
				.andRoute(DELETE("/projects/{id}"), projectHandler::delete);
	}
	
}
