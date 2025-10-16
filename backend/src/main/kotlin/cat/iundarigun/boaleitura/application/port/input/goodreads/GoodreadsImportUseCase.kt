package cat.iundarigun.boaleitura.application.port.input.goodreads

import cat.iundarigun.boaleitura.domain.request.GoodreadsImporterRequest

interface GoodreadsImportUseCase {

    fun execute(file: String): List<GoodreadsImporterRequest>
}