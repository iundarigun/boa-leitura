package cat.iundarigun.boaleitura.infrastructure.database.entity

import io.hypersistence.utils.hibernate.type.array.ListArrayType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Version
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@Entity(name = "ToBeRead")
data class ToBeReadEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    var position: Long,

    var done: Boolean = false,

    var bought: Boolean = false,

    @ManyToOne
    var book: BookEntity,

    @Type(ListArrayType::class)
    var platforms: List<String> = emptyList(),

    @Type(ListArrayType::class)
    var tags: List<String> = emptyList(),

    var notes: String? = null,

    @CreationTimestamp
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Version
    var version: Int = 0
) : UserIdBaseEntity()