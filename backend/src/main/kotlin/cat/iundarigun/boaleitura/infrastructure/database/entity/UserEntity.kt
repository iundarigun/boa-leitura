package cat.iundarigun.boaleitura.infrastructure.database.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.Version
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.type.SqlTypes
import java.time.LocalDateTime

@Entity(name = "User")
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,

    var username: String,

    var encryptedPassword: String,

    @JdbcTypeCode(value = SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    var languageTags: Map<String, String> = mapOf(),

    @JdbcTypeCode(value = SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    var formatTags: Map<String, String> = mapOf(),

    @JdbcTypeCode(value = SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    var platformTags: Map<String, String> = mapOf(),

    @CreationTimestamp
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Version
    var version: Int = 0

)