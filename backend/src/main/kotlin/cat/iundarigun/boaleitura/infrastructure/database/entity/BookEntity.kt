package cat.iundarigun.boaleitura.infrastructure.database.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OrderBy
import jakarta.persistence.Version
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity(name = "Book")
data class BookEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    var goodreadsId: Long? = null,

    var title: String,

    var originalTitle: String? = null,

    var numberOfPages: Int? = null,

    var publisherYear: Int,

    var isbn: String? = null,

    var language: String? = null,

    var originalLanguage: String? = null,

    @ManyToOne
    var author: AuthorEntity,

    @OrderBy("dateRead desc")
    @OneToMany(mappedBy = "book")
    var readings: List<ReadingEntity> = emptyList(),

    @ManyToOne
    var saga: SagaEntity? = null,

    var sagaOrder: Double? = null,

    var sagaMainTitle: Boolean? = null,

    @ManyToOne
    var genre: GenreEntity? = null,

    var urlImage: String? = null,

    var urlImageSmall: String? = null,

    @CreationTimestamp
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Version
    var version: Int = 0
)
