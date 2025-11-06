package cat.iundarigun.boaleitura.application.port.input.goodreads

import cat.iundarigun.boaleitura.domain.request.GoodreadsImporterRequest
import cat.iundarigun.boaleitura.domain.request.GoodreadsRequest

interface GoodreadsImportUseCase {

    fun execute(request: GoodreadsRequest): List<GoodreadsImporterRequest>
}