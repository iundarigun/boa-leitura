package cat.iundarigun.boaleitura.application.port.input.reading

interface DeleteReadingUseCase {
    fun execute(id: Long)
}