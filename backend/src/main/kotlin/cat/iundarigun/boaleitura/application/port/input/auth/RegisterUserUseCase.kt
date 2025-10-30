package cat.iundarigun.boaleitura.application.port.input.auth

import cat.iundarigun.boaleitura.domain.request.RegisterRequest

interface RegisterUserUseCase {
    fun execute(request: RegisterRequest)
}