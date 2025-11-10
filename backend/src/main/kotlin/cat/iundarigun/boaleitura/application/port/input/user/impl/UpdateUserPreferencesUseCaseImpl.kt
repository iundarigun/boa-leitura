package cat.iundarigun.boaleitura.application.port.input.user.impl

import cat.iundarigun.boaleitura.application.port.input.user.UpdateUserPreferencesUseCase
import cat.iundarigun.boaleitura.application.port.output.UserPort
import cat.iundarigun.boaleitura.domain.model.UserPreferences
import cat.iundarigun.boaleitura.domain.security.loggedUser
import cat.iundarigun.boaleitura.exception.UsernameOrPasswordNotMatchException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class UpdateUserPreferencesUseCaseImpl(
    private val userPort: UserPort
) : UpdateUserPreferencesUseCase {

    override fun execute(request: UserPreferences) {
        val user = loggedUser?.name?.let {
            userPort.findByUsername(it)
        }
        if (user == null) {
            throw UsernameOrPasswordNotMatchException(HttpStatus.BAD_REQUEST)
        }
        userPort.save(user.copy(userPreferences = request))
    }
}