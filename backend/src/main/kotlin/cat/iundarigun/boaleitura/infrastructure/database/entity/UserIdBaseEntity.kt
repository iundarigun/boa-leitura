package cat.iundarigun.boaleitura.infrastructure.database.entity

import jakarta.persistence.MappedSuperclass
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
}