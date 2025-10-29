package cat.iundarigun.boaleitura.infrastructure.security.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.security.jwt")
data class JwtProperties(
    val key: String,
    val expirationTimeInMinutes: Long,
)
