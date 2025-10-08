package cat.iundarigun.boaleitura.infrastructure.database

import cat.iundarigun.boaleitura.application.port.output.SagaPort
import cat.iundarigun.boaleitura.domain.request.SagaRequest
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.domain.response.SagaResponse
import cat.iundarigun.boaleitura.exception.SagaNotFoundException
import cat.iundarigun.boaleitura.extensions.merge
import cat.iundarigun.boaleitura.extensions.toEntity
import cat.iundarigun.boaleitura.extensions.toResponse
import cat.iundarigun.boaleitura.infrastructure.database.repository.SagaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SagaAdapter(
    private val sagaRepository: SagaRepository
) : SagaPort {
    override fun find(): PageResponse<SagaResponse> {
        TODO("Not yet implemented")
    }

    @Transactional(readOnly = true)
    override fun existsByName(name: String): Boolean =
        sagaRepository.existsByName(name)

    @Transactional(readOnly = true)
    override fun findByName(name: String): SagaResponse? =
        sagaRepository.findByName(name)?.toResponse()

    @Transactional(readOnly = true)
    override fun findById(id: Long): SagaResponse =
        sagaRepository.findByIdOrderByBooksSagaOrder(id)?.toResponse(true) ?: throw SagaNotFoundException(id)

    override fun sagaBookCount(id: Long): Int {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun save(request: SagaRequest, id: Long?): SagaResponse {
        if (id == null) {
            return sagaRepository.save(request.toEntity()).toResponse()
        }
        val saga = sagaRepository.findById(id)
            .orElseThrow { SagaNotFoundException(id) }

        return sagaRepository.save(saga.merge(request)).toResponse()
    }

    override fun delete(id: Long) {
        TODO("Not yet implemented")
    }
}