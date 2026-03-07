package cat.iundarigun.boaleitura.application.port.input.tbr.impl

import cat.iundarigun.boaleitura.application.port.input.tbr.AddToBeReadUseCase
import cat.iundarigun.boaleitura.application.port.output.ToBeReadPort
import cat.iundarigun.boaleitura.application.port.output.UserPort
import cat.iundarigun.boaleitura.domain.request.ToBeReadRequest
import cat.iundarigun.boaleitura.domain.response.ToBeReadResponse
import cat.iundarigun.boaleitura.domain.security.loggedUser
import cat.iundarigun.boaleitura.exception.ToBeReadAlreadyExistsException
import cat.iundarigun.boaleitura.exception.ToBeReadLimitReachedException
import cat.iundarigun.boaleitura.exception.UserNotFoundException
import org.springframework.stereotype.Component

@Component
class AddToBeReadUseCaseImpl(
    private val toBeReadPort: ToBeReadPort,
    private val userPort: UserPort) : AddToBeReadUseCase {

    override fun execute(request: ToBeReadRequest): ToBeReadResponse {
        if (toBeReadPort.existsByBook(request.bookId)) {
            throw ToBeReadAlreadyExistsException()
        }

        validateTBRLimits()

        return toBeReadPort.save(request)
    }

    private fun validateTBRLimits() {
        loggedUser?.name?.let {
            val tbrLimit = userPort.findByUsername(it)?.userPreferences?.tbrLimit
            if (tbrLimit != null) {
                val total = toBeReadPort.countByDone(false)
                if (total >= tbrLimit) {
                    throw ToBeReadLimitReachedException(tbrLimit)
                }
            }
        }
    }
}