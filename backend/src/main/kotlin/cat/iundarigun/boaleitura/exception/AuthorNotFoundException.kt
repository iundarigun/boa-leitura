package cat.iundarigun.boaleitura.exception

class AuthorNotFoundException(id: Long) : EntityNotFoundException("Author", id)