package cat.iundarigun.boaleitura.application.port.input.book

interface DeleteBookUseCase {
    fun execute(id: Long)
}