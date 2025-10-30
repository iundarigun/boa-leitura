package cat.iundarigun.boaleitura.infrastructure.database.entity

import cat.iundarigun.boaleitura.domain.security.loggedUser
import cat.iundarigun.boaleitura.exception.UserNotFoundException
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import org.hibernate.annotations.Filter
import org.hibernate.annotations.FilterDef
import org.hibernate.annotations.ParamDef

@MappedSuperclass
@FilterDef(
    name = "userIdFilter", parameters =
    [ParamDef(name = "userId", type = Long::class)]
)
@Filter(name = "userIdFilter", condition = "user_id = :userId")
open class UserIdBaseEntity {
    var userId: Long = 0L

    @PrePersist
    fun prePersist() {
        userId = loggedUser?.userId ?: throw UserNotFoundException()
    }
}