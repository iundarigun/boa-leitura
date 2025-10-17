package cat.iundarigun.boaleitura.infrastructure.rest.client

import cat.iundarigun.boaleitura.application.port.output.BookInformationPort
import cat.iundarigun.boaleitura.domain.model.BookInformation
import cat.iundarigun.boaleitura.infrastructure.rest.client.extensions.toBookInformation
import cat.iundarigun.boaleitura.infrastructure.rest.client.spec.GoogleApiClient
import cat.iundarigun.boaleitura.infrastructure.rest.client.spec.OpenLibraryClient
import org.apache.commons.text.similarity.JaroWinklerSimilarity
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.math.max

@Service
class BookInformationAdapter(
    private val openLibraryClient: OpenLibraryClient,
    private val googleApiClient: GoogleApiClient
) : BookInformationPort {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun searchByIsbn(isbn: String): List<BookInformation> {
        val searchByIsbn = searchByKey("ISBN:$isbn")
        if (searchByIsbn.isNotEmpty()) {
            return searchByIsbn
        }
        val googleApiResponse = googleApiClient.searchByIsbn(isbn)
        return googleApiResponse.items.map { it.toBookInformation() }
    }

    override fun searchByTitle(title: String, author: String?): List<BookInformation> {
        return searchByCleanTitle(title.replace("\\(.*\\)".toRegex(), ""), author)
    }

    private fun searchByCleanTitle(title: String, author: String?): List<BookInformation> {
        logger.info("SearchByTitle for title: $title, author: $author")
        val openLibraryResults = searchByTitleUsingOpenLibrary(title, author)
        val isbnList = openLibraryResults.mapNotNull { it.isbn }
        val googleApiResults = searchByUsingGoogleApi(title, author, isbnList)

        return openLibraryResults.plus(googleApiResults)
            .sortedBy { title.similarityRatio(it.title) * (author?.similarityRatio(it.author) ?: 1.0) }
    }

    private fun searchByTitleUsingOpenLibrary(
        title: String,
        author: String?
    ): List<BookInformation> {
        val searchByTitle = openLibraryClient.searchByTitle(title)
            .also {
                logger.info("openLibrary find ${it.docs.size} docs")
            }
        val filteredDocs = searchByTitle.docs
            .filter {
                it.coverEditionKey != null
            }.filter { doc ->
                title.similarityRatio(doc.title) > RATIO_THRESHOLD &&
                        (author.isNullOrBlank() || doc.authorName.any { author.similarityRatio(it) > RATIO_THRESHOLD })
            }.also {
                logger.info("openLibrary filterSimilarity ${it.size} docs")
            }
        return filteredDocs.map { searchByKey(it.coverEditionKey!!) }.flatten()
    }

    private fun searchByKey(key: String): List<BookInformation> {
        val searchByIsbn = openLibraryClient.searchByKey(key)
        return searchByIsbn.values.map {
            if (it.getIsbn() != null) {
                val googleApiResponse = googleApiClient.searchByIsbn(it.getIsbn()!!)
                    .items.firstOrNull()
                it.toBookInformation(googleApiResponse)
            } else {
                it.toBookInformation()
            }
        }
    }

    private fun searchByUsingGoogleApi(
        title: String,
        author: String?,
        isbnList: List<String> = emptyList()
    ): List<BookInformation> {
        logger.info("SearchByTitleFallback for author: $title, author: $author")
        val searchByTitle = googleApiClient.searchByTitle(title)
            .also { logger.info("googleApi find ${it.items.size} items") }
        return searchByTitle.items
            .filter { item -> item.getIsbn().isNullOrBlank() || !isbnList.contains(item.getIsbn()) }
            .filter { item ->
                max(
                    title.similarityRatio(item.volumeInfo.getFullTitle()),
                    title.similarityRatio(item.volumeInfo.title)
                ) > RATIO_THRESHOLD &&
                        (author.isNullOrBlank() ||
                                item.volumeInfo.authors.any { author.similarityRatio(it) > RATIO_THRESHOLD })
            }
            .map { it.toBookInformation() }
//            .sortedBy { title.similarityRatio(it.title) * author.similarityRatio(it.author) }
    }

    companion object {
        private val JW = JaroWinklerSimilarity()
        private const val RATIO_THRESHOLD = 0.9
        private fun String?.similarityRatio(other: String?): Double {
            if (this.isNullOrBlank() || other.isNullOrBlank()) {
                return 0.0
            }
            return JW.apply(this.lowercase(), other.lowercase())
        }
    }
}