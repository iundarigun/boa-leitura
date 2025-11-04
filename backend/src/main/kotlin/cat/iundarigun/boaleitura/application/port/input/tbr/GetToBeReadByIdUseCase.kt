package cat.iundarigun.boaleitura.application.port.input.tbr

import cat.iundarigun.boaleitura.domain.response.ToBeReadResponse

interface GetToBeReadByIdUseCase {
    fun execute(id: Long): ToBeReadResponse
}