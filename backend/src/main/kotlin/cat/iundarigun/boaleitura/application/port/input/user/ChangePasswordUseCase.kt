package cat.iundarigun.boaleitura.application.port.input.user

import cat.iundarigun.boaleitura.domain.request.ChangePasswordRequest

interface ChangePasswordUseCase {
    fun execute(request: ChangePasswordRequest)
}