package cat.iundarigun.boaleitura.application.port.input.tbr

import cat.iundarigun.boaleitura.domain.request.ToBeReadReorderRequest

interface ReorderToBeReadUseCase {
    fun execute(id: Long, request: ToBeReadReorderRequest)
}