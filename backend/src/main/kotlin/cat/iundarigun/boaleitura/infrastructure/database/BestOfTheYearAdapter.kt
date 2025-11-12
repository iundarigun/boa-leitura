package cat.iundarigun.boaleitura.infrastructure.database

import cat.iundarigun.boaleitura.application.port.output.BestOfTheYearPort
import cat.iundarigun.boaleitura.domain.request.BestOfTheYearRequest
import cat.iundarigun.boaleitura.domain.response.BestOfTheYearResponse
import cat.iundarigun.boaleitura.exception.BestOfTheYearNotFoundException
import cat.iundarigun.boaleitura.infrastructure.database.extensions.merge
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toEntity
import cat.iundarigun.boaleitura.infrastructure.database.extensions.toResponse
import cat.iundarigun.boaleitura.infrastructure.database.repository.BestOfTheYearRepository
import cat.iundarigun.boaleitura.infrastructure.database.repository.ReadingRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BestOfTheYearAdapter(
    private val bestOfTheYearRepository: BestOfTheYearRepository,
    private val readingRepository: ReadingRepository
) : BestOfTheYearPort {
    @Transactional(readOnly = true)
    override fun findByYear(year: Int): BestOfTheYearResponse? =
        bestOfTheYearRepository.findByYear(year)?.toResponse()

    @Transactional
    override fun save(request: BestOfTheYearRequest): BestOfTheYearResponse {
        if (request.id == null) {
            return bestOfTheYearRepository.save(
                request.toEntity { id: Long -> readingRepository.getReferenceById(id) })
                .toResponse()
        }
        val bestOfTheYear = bestOfTheYearRepository.findById(request.id)
            .orElseThrow { BestOfTheYearNotFoundException() }

        return bestOfTheYearRepository.save(
            bestOfTheYear.merge(request) { id: Long -> readingRepository.getReferenceById(id) })
            .toResponse()
    }
}