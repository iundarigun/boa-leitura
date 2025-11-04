package cat.iundarigun.boaleitura.application.port.input.tbr.impl

import cat.iundarigun.boaleitura.application.port.input.tbr.FindToBeReadUseCase
import cat.iundarigun.boaleitura.application.port.output.ToBeReadPort
import cat.iundarigun.boaleitura.domain.request.SearchToBeReadRequest
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.domain.response.ToBeReadResponse
import org.springframework.stereotype.Component

@Component
class FindToBeReadUseCaseImpl(private val toBeReadPort: ToBeReadPort) : FindToBeReadUseCase {

    override fun execute(request: SearchToBeReadRequest): PageResponse<ToBeReadResponse> =
        toBeReadPort.find(request.keyword, request.bought, request.done, request.toPageRequest())
}