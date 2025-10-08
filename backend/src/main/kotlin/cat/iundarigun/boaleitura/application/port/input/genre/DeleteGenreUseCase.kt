package cat.iundarigun.boaleitura.application.port.input.genre

interface DeleteGenreUseCase {
    fun execute(id: Long)
}