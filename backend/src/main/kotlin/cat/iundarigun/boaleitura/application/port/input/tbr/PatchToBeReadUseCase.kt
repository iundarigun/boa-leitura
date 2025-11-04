package cat.iundarigun.boaleitura.application.port.input.tbr

interface PatchToBeReadUseCase {
    fun execute(id: Long, request: Map<String, Any>)
}