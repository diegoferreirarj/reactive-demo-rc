package com.example.reactivedemorc.config

import com.newrelic.telemetry.Attributes
import io.micrometer.NewRelicRegistryConfig
import io.micrometer.core.instrument.util.NamedThreadFactory
import io.micrometer.newrelic.NewRelicRegistry
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration
import org.springframework.boot.actuate.autoconfigure.metrics.export.simple.SimpleMetricsExportAutoConfiguration
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.InetAddress
import java.net.UnknownHostException
import java.time.Duration


@Configuration
@AutoConfigureBefore(CompositeMeterRegistryAutoConfiguration::class, SimpleMetricsExportAutoConfiguration::class)
@AutoConfigureAfter(MetricsAutoConfiguration::class)
@ConditionalOnClass(NewRelicRegistry::class)
class MicrometerConfig {
    @Bean
    fun newRelicConfig(): NewRelicRegistryConfig {
        return object : NewRelicRegistryConfig {
            override fun apiKey(): String {
                return System.getenv("NR_INSERT_API_KEY")
            }

            override fun uri(): String {
                return System.getenv("NR_METRIC_URI")
            }

            override fun get(key: String): String? {
                return null
            }

            override fun step(): Duration {
                return Duration.ofSeconds(5)
            }

            @Value("\${spring.application.name}")
            var serviceName: String? = null
            override fun serviceName(): String? {
                return serviceName
            }
        }
    }

    @Bean
    @Throws(UnknownHostException::class)
    fun newRelicMeterRegistry(config: NewRelicRegistryConfig): NewRelicRegistry {
        val newRelicRegistry: NewRelicRegistry = NewRelicRegistry.builder(config)
                .commonAttributes(
                        Attributes()
                                .put("host.hostname", InetAddress.getLocalHost().hostName))
                .build()
        newRelicRegistry.start(NamedThreadFactory("newrelic.micrometer.registry"))
        return newRelicRegistry
    }
}