package cat.iundarigun.boaleitura

import cat.iundarigun.boaleitura.infrastructure.security.configuration.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.retry.annotation.EnableRetry

@EnableRetry
@EnableAspectJAutoProxy
@SpringBootApplication
@EnableConfigurationProperties(JwtProperties::class)
class BoaLeituraApplication

fun main(args: Array<String>) {
	runApplication<BoaLeituraApplication>(*args)
}
