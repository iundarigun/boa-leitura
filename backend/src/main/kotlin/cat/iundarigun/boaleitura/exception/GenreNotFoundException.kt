package cat.iundarigun.boaleitura.exception

class GenreNotFoundException(id: Long) : EntityNotFoundException("Genre", id)