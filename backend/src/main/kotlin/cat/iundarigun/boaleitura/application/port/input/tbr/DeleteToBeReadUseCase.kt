package cat.iundarigun.boaleitura.application.port.input.tbr

interface DeleteToBeReadUseCase {
    fun execute(id: Long)
}