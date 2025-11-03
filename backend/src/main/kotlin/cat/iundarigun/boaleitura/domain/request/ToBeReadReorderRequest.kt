package cat.iundarigun.boaleitura.domain.request

data class ToBeReadReorderRequest(
    val targetId: Long,
    val direction: ReorderDirectionEnum
) {
    enum class ReorderDirectionEnum {
        UP, DOWN
    }
}