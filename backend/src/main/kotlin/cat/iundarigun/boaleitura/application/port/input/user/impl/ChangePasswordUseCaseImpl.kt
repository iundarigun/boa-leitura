package cat.iundarigun.boaleitura.application.port.input.user.impl

import cat.iundarigun.boaleitura.application.port.input.user.ChangePasswordUseCase
import cat.iundarigun.boaleitura.application.port.output.SecurityPort
import cat.iundarigun.boaleitura.application.port.output.UserPort
import cat.iundarigun.boaleitura.domain.request.ChangePasswordRequest
import cat.iundarigun.boaleitura.domain.security.loggedUser
import cat.iundarigun.boaleitura.exception.UsernameOrPasswordNotMatchException
import org.springframework.stereotype.Component

@Component
class ChangePasswordUseCaseImpl(
    private val userPort: UserPort,
    private val securityPort: SecurityPort
) : ChangePasswordUseCase {

    override fun execute(request: ChangePasswordRequest) {
        val user = loggedUser?.name?.let {
            userPort.findByUsername(it)
        }
        if (user == null || securityPort.matches(request.oldPassword, user.encryptedPassword)) {
            throw UsernameOrPasswordNotMatchException()
        }
        userPort.save(user.copy(encryptedPassword = securityPort.encryptPassword(request.password)))
    }
}