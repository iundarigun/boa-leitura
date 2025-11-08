package cat.iundarigun.boaleitura.application.port.input.user.impl

import cat.iundarigun.boaleitura.application.port.input.user.GetUserUseCase
import cat.iundarigun.boaleitura.application.port.output.UserPort
import cat.iundarigun.boaleitura.domain.response.UserResponse
import cat.iundarigun.boaleitura.domain.security.loggedUser
import cat.iundarigun.boaleitura.exception.UserNotFoundException
import org.springframework.stereotype.Component

@Component
class GetUserUseCaseImpl(private val userPort: UserPort) : GetUserUseCase {
    override fun execute(): UserResponse {
        val user = loggedUser?.name?.let {
            userPort.findByUsername(it)
        } ?: throw UserNotFoundException()

        return UserResponse(user.username, user.userPreferences)
    }
}