package cat.iundarigun.boaleitura.application.port.input.goodreads

import cat.iundarigun.boaleitura.domain.request.GoodreadsImporterRequest
import java.io.InputStream

interface GoodreadsImportUseCase {

    fun execute(file: InputStream): List<GoodreadsImporterRequest>
}