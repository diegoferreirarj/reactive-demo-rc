package com.example.reactivedemorc

import io.r2dbc.spi.ConnectionFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.data.domain.ReactiveAuditorAware
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator
import reactor.core.publisher.Mono


@SpringBootApplication
@EnableR2dbcRepositories("com.example")
@EnableR2dbcAuditing
class ReactiveDemoRcApplication {
	@Bean
	fun auditorAware(): ReactiveAuditorAware<String>? {
		return ReactiveAuditorAware { Mono.just("movilepay") }
	}

	@Bean
	fun initializer(connectionFactory: ConnectionFactory?): ConnectionFactoryInitializer? {
		val initializer = ConnectionFactoryInitializer()
		initializer.setConnectionFactory(connectionFactory!!)

		val populator = CompositeDatabasePopulator()
		populator.addPopulators(ResourceDatabasePopulator(ClassPathResource("schema.sql")))

		initializer.setDatabasePopulator(populator)

		return initializer
	}
}

fun main(args: Array<String>) {
	runApplication<ReactiveDemoRcApplication>(*args)
}
