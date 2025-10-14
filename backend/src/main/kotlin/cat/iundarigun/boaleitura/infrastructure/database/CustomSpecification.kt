package cat.iundarigun.boaleitura.infrastructure.database

import jakarta.persistence.criteria.Join
import jakarta.persistence.criteria.JoinType
import org.springframework.data.jpa.domain.Specification

fun <T> specLike(value: String?, fieldName: String) = Specification<T> { root, _, criteriaBuilder ->
    if (value.isNullOrBlank().not()) {
        criteriaBuilder.and(
            criteriaBuilder.like(criteriaBuilder.upper(root.get(fieldName)), "%${value?.uppercase()}%")
        )
    } else {
        criteriaBuilder.and()
    }
}

@Suppress("SpreadOperator")
fun <T> specLikeWithOrFields(value: String?, vararg fieldNames: String): Specification<T> =
    Specification<T> { root, _, criteriaBuilder ->
        if (fieldNames.size < 2) {
            throw IllegalArgumentException("At least 2 fields must be specified")
        }
        if (value.isNullOrBlank().not()) {
            val joinMap = fieldNames.filter { it.contains(".") }
                .map { it.split(".").first() }
                .toSet()
                .associateWith { root.join<T, Any>(it, JoinType.LEFT) }

            val predicateList = fieldNames.map {
                if (it.contains(".")) {
                    val join = joinMap[it.split(".").first()]
                    criteriaBuilder.like(criteriaBuilder.upper(join?.get(it.split(".").last())),
                        "%${value?.uppercase()}%")
                } else {
                    criteriaBuilder.like(
                        criteriaBuilder.upper(root.get(it)),
                        "%${value?.uppercase()}%"
                    )
                }
            }

            criteriaBuilder.and(criteriaBuilder.or(*predicateList.toTypedArray()))
        } else {
            criteriaBuilder.and()
        }
    }

fun <T> specExistsOrNot(exists: Boolean?, fieldListName: String, countFieldName: String = "id") =
    Specification<T> { root, _, criteriaBuilder ->
        exists?.let {
            val join: Join<T, Any> = root.join(fieldListName, JoinType.LEFT)
            if (exists) {
                criteriaBuilder.and(criteriaBuilder.isNotNull(join.get<Any>(countFieldName)))
            } else {
                criteriaBuilder.and(criteriaBuilder.isNull(join.get<Any>(countFieldName)))
            }
        } ?: criteriaBuilder.and()
    }

fun <T> specIsNull(fieldName: String) =
    Specification<T> { root, _, criteriaBuilder ->
        criteriaBuilder.and(criteriaBuilder.isNull(root.get<Any>(fieldName)))
    }

fun <T> specIsNotNull(fieldName: String) =
    Specification<T> { root, _, criteriaBuilder ->
        criteriaBuilder.and(criteriaBuilder.isNotNull(root.get<Any>(fieldName)))
    }