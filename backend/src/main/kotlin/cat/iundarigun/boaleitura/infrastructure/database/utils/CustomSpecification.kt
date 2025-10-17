package cat.iundarigun.boaleitura.infrastructure.database.utils

import jakarta.persistence.criteria.Join
import jakarta.persistence.criteria.JoinType
import jakarta.persistence.criteria.Root
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
            val joinMap = joinMap(root, fieldNames)

            val predicateList = fieldNames.map {
                if (it.contains(".")) {
                    val decomposedField = it.split(".")
                    val joinName = decomposedField.subList(0, decomposedField.lastIndex).joinToString(".")
                    val join = joinMap[joinName]
                    criteriaBuilder.like(
                        criteriaBuilder.upper(join?.get(decomposedField.last())),
                        "%${value?.uppercase()}%"
                    )
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

fun <T, V : Comparable<V>> specGreaterOrEqualsThan(fieldName: String, value: V?) =
    Specification<T> { root, _, criteriaBuilder ->
        value?.let {
            criteriaBuilder.and(
                criteriaBuilder.greaterThanOrEqualTo(root.get(fieldName), value)
            )
        } ?: criteriaBuilder.and()
    }

fun <T, V : Comparable<V>> specLessOrEqualsThan(fieldName: String, value: V?) =
    Specification<T> { root, _, criteriaBuilder ->
        value?.let {
            criteriaBuilder.and(
                criteriaBuilder.lessThanOrEqualTo(root.get(fieldName), value)
            )
        } ?: criteriaBuilder.and()
    }

@Suppress("MagicNumber")
private fun <T> joinMap(root: Root<T>, fieldNames: Array<out String>): Map<String, Join<out Any?, Any>?> {
    val joinMapFirstLevel = fieldNames.filter { it.contains(".") }
        .map { it.split(".").first() }
        .toSet()
        .associateWith { root.join<T, Any>(it, JoinType.LEFT) }

    val joinMapSecondLevel = fieldNames.filter { it.split(".").size == 3 }
        .map { Pair(it.split(".").first(), it.split(".")[1]) }
        .toSet().associate {
            "${it.first}.${it.second}" to
                    joinMapFirstLevel[it.first]?.join<Any, Any>(it.second, JoinType.LEFT)
        }

    return joinMapFirstLevel.plus(joinMapSecondLevel)
}
