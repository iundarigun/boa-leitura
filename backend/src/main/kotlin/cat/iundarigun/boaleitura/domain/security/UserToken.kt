package cat.iundarigun.boaleitura.domain.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken

class UserToken(jwt: Jwt) : JwtAuthenticationToken(jwt, emptySet()) {
    val userId: Long = jwt.getClaim("userId")
}
val loggedUser: UserToken?
    get() = SecurityContextHolder.getContext().authentication as? UserToken