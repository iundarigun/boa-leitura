package cat.iundarigun.boaleitura.infrastructure.database.repository

import cat.iundarigun.boaleitura.domain.entity.GenreEntity
import org.springframework.data.repository.CrudRepository

interface GenreRepository : CrudRepository<GenreEntity, Long>