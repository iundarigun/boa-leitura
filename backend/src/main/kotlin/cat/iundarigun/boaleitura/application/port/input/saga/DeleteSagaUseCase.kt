package cat.iundarigun.boaleitura.application.port.input.saga

interface DeleteSagaUseCase {
    fun execute(id: Long)
}