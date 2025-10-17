package cat.iundarigun.boaleitura.domain.request

import jakarta.validation.constraints.AssertTrue

data class BookInformationRequest(
    val isbn: String?,
    val title: String?,
    val author: String?
) {
    @AssertTrue(message = "Options: isbn without any other, title without any other, or title and author without isbn")
    fun isFilters(): Boolean =
        isbn.isNullOrBlank().not() && title.isNullOrBlank() && author.isNullOrBlank() ||
                title.isNullOrBlank().not() && isbn.isNullOrBlank()
}