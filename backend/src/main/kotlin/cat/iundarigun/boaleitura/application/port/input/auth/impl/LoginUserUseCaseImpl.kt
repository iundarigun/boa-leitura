package cat.iundarigun.boaleitura.application.port.input.auth.impl

import cat.iundarigun.boaleitura.application.port.input.auth.LoginUserUseCase
import cat.iundarigun.boaleitura.application.port.output.SecurityPort
import cat.iundarigun.boaleitura.application.port.output.UserPort
import cat.iundarigun.boaleitura.domain.request.LoginRequest
import cat.iundarigun.boaleitura.exception.UsernameOrPasswordNotMatchException
import org.springframework.stereotype.Component

@Component
class LoginUserUseCaseImpl(
    private val userPort: UserPort,
    private val securityPort: SecurityPort
) : LoginUserUseCase {

    override fun execute(request: LoginRequest): String {
        val user = userPort.findByUsername(request.username)
        if (user == null ||
            !securityPort.matches(request.password, user.encryptedPassword)
        ) {
            throw UsernameOrPasswordNotMatchException()
        }

        return securityPort.buildJwt(user)
    }
}