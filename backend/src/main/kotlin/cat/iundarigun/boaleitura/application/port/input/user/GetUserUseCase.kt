package cat.iundarigun.boaleitura.application.port.input.user

import cat.iundarigun.boaleitura.domain.response.UserResponse

interface GetUserUseCase {
    fun execute(): UserResponse
}