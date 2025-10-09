package cat.iundarigun.boaleitura.infrastructure.database.repository

import cat.iundarigun.boaleitura.domain.entity.GenreEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface GenreRepository : JpaRepository<GenreEntity, Long> {
    fun existsByName(name: String): Boolean
    fun findByName(name: String): GenreEntity?
    @EntityGraph(attributePaths = ["subGenres"])
    fun findByParentNull(pageable: Pageable): Page<GenreEntity>
    fun existsByParentId(id: Long): Boolean

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

    @Query("""
        select g from Genre g
        left join g.subGenres sg
        where g.parent is null and 
        (upper(g.name) like '%' || upper(:name) || '%' or
        upper(sg.name) like '%' || upper(:name) || '%')
        """)
    fun findFirstLevelByNameLike(name: String, pageable: Pageable): Page<GenreEntity>
}