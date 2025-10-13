package cat.iundarigun.boaleitura.exception

class BookNotFoundException(id: Long) : EntityNotFoundException("Book", id)