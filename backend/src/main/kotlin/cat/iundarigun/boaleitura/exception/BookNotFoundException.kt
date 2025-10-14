package cat.iundarigun.boaleitura.exception

class BookNotFoundException(id: Long? = null) : EntityNotFoundException("Book", id)