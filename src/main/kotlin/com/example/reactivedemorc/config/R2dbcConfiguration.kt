package com.example.reactivedemorc.config

import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.zonky.test.db.postgres.embedded.EmbeddedPostgres

@Configuration
@EnableR2dbcRepositories
class R2dbcConfiguration : AbstractR2dbcConfiguration() {

    override fun connectionFactory(): ConnectionFactory {
        return PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
//                        .host("localhost")
                        .port(50003)
                        .username("postgres")
                        .password("postgres")
                        .build()
                );
    }


}