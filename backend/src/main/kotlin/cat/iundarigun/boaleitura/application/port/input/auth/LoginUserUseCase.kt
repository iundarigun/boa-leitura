package cat.iundarigun.boaleitura.application.port.input.auth

import cat.iundarigun.boaleitura.domain.request.LoginRequest
import cat.iundarigun.boaleitura.domain.response.LoginResponse

interface LoginUserUseCase {
    fun execute(request: LoginRequest): LoginResponse
}