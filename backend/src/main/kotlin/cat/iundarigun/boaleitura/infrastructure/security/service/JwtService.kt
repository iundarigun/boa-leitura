package cat.iundarigun.boaleitura.infrastructure.security.service

import cat.iundarigun.boaleitura.domain.model.User
import cat.iundarigun.boaleitura.exception.UsernameOrPasswordNotMatchException
import cat.iundarigun.boaleitura.infrastructure.security.configuration.JwtProperties
import cat.iundarigun.boaleitura.infrastructure.security.configuration.SecurityConfiguration
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.oauth2.jwt.JwsHeader
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID

@Service
class JwtService(
    private val jwtEncoder: JwtEncoder,
    private val jwtDecoder: JwtDecoder,
    private val jwtProperties: JwtProperties,
    @Value("\${spring.application.name}")
    private val applicationName: String
) {

    fun buildJwt(user: User): String {
        val claims = JwtClaimsSet.builder()
            .subject(user.username)
            .id(UUID.randomUUID().toString())
            .issuer(applicationName)
            .expiresAt(Instant.now().plus(jwtProperties.expirationTimeInMinutes, ChronoUnit.MINUTES))
            .issuedAt(Instant.now())
            .claim("userId", user.id ?: throw UsernameOrPasswordNotMatchException())
            .build()

        val header = JwsHeader.with(SecurityConfiguration.JWT_ALGORITHM).build()
        val parameters = JwtEncoderParameters.from(header, claims)
        return jwtEncoder.encode(parameters).tokenValue
    }

    private fun validateToken(token: String): Boolean {
        val decode = jwtDecoder.decode(token)
        return decode.subject != null
    }
}