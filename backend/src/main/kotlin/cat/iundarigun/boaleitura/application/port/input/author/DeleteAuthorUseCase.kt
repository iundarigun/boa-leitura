package cat.iundarigun.boaleitura.application.port.input.author

interface DeleteAuthorUseCase {
    fun execute(id: Long)
}