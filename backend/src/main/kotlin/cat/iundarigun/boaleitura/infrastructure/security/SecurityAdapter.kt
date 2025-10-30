package cat.iundarigun.boaleitura.infrastructure.security

import cat.iundarigun.boaleitura.application.port.output.SecurityPort
import cat.iundarigun.boaleitura.domain.model.User
import cat.iundarigun.boaleitura.domain.response.LoginResponse
import cat.iundarigun.boaleitura.infrastructure.security.service.JwtService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class SecurityAdapter(
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService

) : SecurityPort {

    override fun encryptPassword(password: String): String =
        passwordEncoder.encode(password)

    override fun matches(loginPassword: String, savedEncryptedPassword: String): Boolean =
        passwordEncoder.matches(loginPassword, savedEncryptedPassword)

    override fun generateLoginToken(user: User): LoginResponse {
        return LoginResponse(jwtService.buildJwt(user))
    }
}