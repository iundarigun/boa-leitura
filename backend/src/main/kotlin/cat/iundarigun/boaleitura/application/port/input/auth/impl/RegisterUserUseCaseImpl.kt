package cat.iundarigun.boaleitura.application.port.input.auth.impl

import cat.iundarigun.boaleitura.application.port.input.auth.RegisterUserUseCase
import cat.iundarigun.boaleitura.application.port.output.SecurityPort
import cat.iundarigun.boaleitura.application.port.output.UserPort
import cat.iundarigun.boaleitura.domain.model.User
import cat.iundarigun.boaleitura.domain.request.RegisterRequest
import cat.iundarigun.boaleitura.exception.UserAlreadyExistsException
import org.springframework.stereotype.Component

@Component
class RegisterUserUseCaseImpl(private val userPort: UserPort, private val securityPort: SecurityPort) :
    RegisterUserUseCase {

    override fun execute(request: RegisterRequest) {
        if (userPort.existsByUsername(request.username)) {
            throw UserAlreadyExistsException(request.username)
        }
        val user = User(
            username = request.username,
            encryptedPassword = securityPort.encryptPassword(request.password)
        )
        userPort.save(user)
    }
}