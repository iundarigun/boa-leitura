package cat.iundarigun.boaleitura.exception

class SagaNotFoundException(id: Long) : EntityNotFoundException("Saga", id)