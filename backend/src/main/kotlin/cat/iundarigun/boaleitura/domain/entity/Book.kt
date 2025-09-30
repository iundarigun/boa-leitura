package cat.iundarigun.boaleitura.domain.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Version
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    var goodreadsId: Long,

    var title: String,

    var numberOfPages: Int? = null,

    var publisherYear: Int,

    var isbn: String? = null,

    var isbn13: String? = null,

    var originalLanguage: String? = null,

    @ManyToOne
    var author: Author,

    @ManyToOne
    var saga: Saga? = null,

    @CreationTimestamp
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Version
    var version: Int = 0
)
