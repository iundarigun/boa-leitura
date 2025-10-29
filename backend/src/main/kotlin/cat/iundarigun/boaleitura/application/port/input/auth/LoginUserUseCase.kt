package cat.iundarigun.boaleitura.application.port.input.auth

import cat.iundarigun.boaleitura.domain.request.LoginRequest

interface LoginUserUseCase {
    fun execute(request: LoginRequest): String
}