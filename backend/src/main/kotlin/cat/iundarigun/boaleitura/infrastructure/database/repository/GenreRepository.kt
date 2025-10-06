package cat.iundarigun.boaleitura.infrastructure.database.repository

import cat.iundarigun.boaleitura.domain.entity.GenreEntity
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface GenreRepository : CrudRepository<GenreEntity, Long> {
    fun existsByName(name: String): Boolean
    fun findByName(name: String): GenreEntity?
    @EntityGraph(attributePaths = ["subGenres"])
    fun findByParentNull(): List<GenreEntity>

    @Query("""
    WITH RECURSIVE genre_hierarchy AS (
        SELECT g.id, g.name, g.parent_genre_id
        FROM genre g
        WHERE g.id = :startId
    UNION ALL
        SELECT g.id, g.name, g.parent_genre_id
        FROM genre g
        INNER JOIN genre_hierarchy gh ON g.id = gh.parent_genre_id
    ) SELECT count(*) > 0 from genre_hierarchy where id = :parentId
    """, nativeQuery = true)
    fun existsParentIdInHierarchy(startId: Long, parentId: Long): Boolean
}