package com.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.reactive.TransactionalOperator;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;

@Configuration
@EnableR2dbcRepositories
@EnableTransactionManagement
public class PostgresConfig extends AbstractR2dbcConfiguration {

	@Bean
	@Override
	public ConnectionFactory connectionFactory() {
		return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
				.host("localhost")
				.port(5432)
				.username("postgres")
				.password("admin")
				.database("projects").build());
	}

	@Bean
	ReactiveTransactionManager reactiveTransactionManager(ConnectionFactory connectionFactory) {
		return new R2dbcTransactionManager(connectionFactory);
	}

	@Bean
	TransactionalOperator transactionalOperator(ReactiveTransactionManager reactiveTransactionManager) {
		return TransactionalOperator.create(reactiveTransactionManager);
	}

	@Bean
	public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
		ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
		initializer.setConnectionFactory(connectionFactory);
		ResourceDatabasePopulator populator = 
				new ResourceDatabasePopulator(new ClassPathResource("projects-schema.sql"));
		initializer.setDatabasePopulator(populator);
		return initializer;
	}

}
