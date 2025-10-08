package cat.iundarigun.boaleitura.infrastructure.database

import cat.iundarigun.boaleitura.application.port.output.SagaPort
import cat.iundarigun.boaleitura.domain.request.SagaRequest
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.domain.response.SagaResponse
import org.springframework.stereotype.Service

@Service
class SagaAdapter : SagaPort {
    override fun find(): PageResponse<SagaResponse> {
        TODO("Not yet implemented")
    }

    override fun existsByName(name: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun findByName(name: String): SagaResponse? {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): SagaResponse {
        TODO("Not yet implemented")
    }

    override fun sagaBookCount(id: Long): Int {
        TODO("Not yet implemented")
    }

    override fun save(request: SagaRequest, id: Long?): SagaResponse {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long) {
        TODO("Not yet implemented")
    }
}