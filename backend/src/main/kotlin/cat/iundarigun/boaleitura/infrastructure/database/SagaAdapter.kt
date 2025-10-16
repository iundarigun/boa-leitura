package cat.iundarigun.boaleitura.infrastructure.database

import cat.iundarigun.boaleitura.application.port.output.SagaPort
import cat.iundarigun.boaleitura.domain.request.PageRequest
import cat.iundarigun.boaleitura.domain.request.SagaRequest
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.domain.response.SagaResponse
import cat.iundarigun.boaleitura.exception.SagaNotFoundException
import cat.iundarigun.boaleitura.domain.extensions.merge
import cat.iundarigun.boaleitura.domain.extensions.toEntity
import cat.iundarigun.boaleitura.domain.extensions.toPageResponse
import cat.iundarigun.boaleitura.domain.extensions.toPageable
import cat.iundarigun.boaleitura.domain.extensions.toResponse
import cat.iundarigun.boaleitura.infrastructure.database.repository.BookRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.SagaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SagaAdapter(
    private val sagaRepository: SagaRepository,
    private val bookRepository: BookRepository,
) : SagaPort {

    @Transactional(readOnly = true)
    override fun find(name: String?, pageRequest: PageRequest): PageResponse<SagaResponse> =
        sagaRepository.findAll(specLike(name, "name"), pageRequest.toPageable())
            .map { it.toResponse() }
            .toPageResponse()

    @Transactional(readOnly = true)
    override fun existsByName(name: String): Boolean =
        sagaRepository.existsByName(name)

    @Transactional(readOnly = true)
    override fun findByName(name: String): SagaResponse? =
        sagaRepository.findByName(name)?.toResponse()

    @Transactional(readOnly = true)
    override fun findById(id: Long): SagaResponse =
        sagaRepository.findByIdOrderByBooksSagaOrder(id)?.toResponse(true) ?: throw SagaNotFoundException(id)

    @Transactional(readOnly = true)
    override fun existsById(id: Long): Boolean =
        sagaRepository.existsById(id)

    @Transactional(readOnly = true)
    override fun sagaBookCount(id: Long): Int =
        bookRepository.countBySagaId(id)

    @Transactional
    override fun save(request: SagaRequest, id: Long?): SagaResponse {
        if (id == null) {
            return sagaRepository.save(request.toEntity()).toResponse()
        }
        val saga = sagaRepository.findById(id)
            .orElseThrow { SagaNotFoundException(id) }

        return sagaRepository.save(saga.merge(request)).toResponse()
    }

    @Transactional
    override fun delete(id: Long) {
        if (!sagaRepository.existsById(id)) {
            throw SagaNotFoundException(id)
        }
        sagaRepository.deleteById(id)
    }
}