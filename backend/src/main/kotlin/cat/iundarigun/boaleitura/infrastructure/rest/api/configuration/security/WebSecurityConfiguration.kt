package cat.iundarigun.boaleitura.infrastructure.rest.api.configuration.security

import cat.iundarigun.boaleitura.domain.security.UserToken
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class WebSecurityConfiguration(private val jwtDecoder: JwtDecoder) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http.csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/swagger-ui/**", "/*/api-docs/**", "/auth/**")
                    .permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2ResourceServer { resourceServer ->
                resourceServer.jwt { jwtConfigurer ->
                    jwtConfigurer.decoder(jwtDecoder)
                    jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter())
                }
            }
            .build()
    }

    private fun jwtAuthenticationConverter(): Converter<Jwt, AbstractAuthenticationToken> {
        return Converter<Jwt, AbstractAuthenticationToken> { UserToken(it) }
    }
}