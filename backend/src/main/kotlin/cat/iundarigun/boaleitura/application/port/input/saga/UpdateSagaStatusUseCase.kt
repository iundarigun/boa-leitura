package cat.iundarigun.boaleitura.application.port.input.saga

import cat.iundarigun.boaleitura.domain.enums.SagaStatusEnum
import cat.iundarigun.boaleitura.domain.response.SagaResponse

interface UpdateSagaStatusUseCase {
    fun execute(id: Long, request: SagaStatusEnum): SagaResponse
}
