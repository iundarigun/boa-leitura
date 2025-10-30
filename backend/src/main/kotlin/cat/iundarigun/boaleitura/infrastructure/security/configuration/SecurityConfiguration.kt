package cat.iundarigun.boaleitura.infrastructure.security.configuration

import com.nimbusds.jose.jwk.source.ImmutableSecret
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import javax.crypto.spec.SecretKeySpec

@Configuration
class SecurityConfiguration(
    private val jwtProperties: JwtProperties
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(PASSWORD_ENCRYPT_STRENGTH)
    }

    @Bean
    fun jwtEncoder(): JwtEncoder {
        val secretKey = SecretKeySpec(jwtProperties.key.toByteArray(), JWT_ALGORITHM.name)
        val jwk = ImmutableSecret<SecurityContext>(secretKey)
        return NimbusJwtEncoder(jwk)
    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        val secretKey = SecretKeySpec(jwtProperties.key.toByteArray(), JWT_ALGORITHM.name)
        return NimbusJwtDecoder.withSecretKey(secretKey).build()
    }

    companion object {
        private const val PASSWORD_ENCRYPT_STRENGTH = 12
        val JWT_ALGORITHM = MacAlgorithm.HS256
    }
}