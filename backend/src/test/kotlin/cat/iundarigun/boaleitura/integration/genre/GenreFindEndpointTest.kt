package cat.iundarigun.boaleitura.integration.genre

import cat.iundarigun.boaleitura.configuration.TestContainerBaseConfiguration
import cat.iundarigun.boaleitura.domain.response.ErrorResponse
import cat.iundarigun.boaleitura.domain.response.GenreResponse
import cat.iundarigun.boaleitura.domain.response.PageResponse
import cat.iundarigun.boaleitura.factory.GenreEntityFactory
import cat.iundarigun.boaleitura.infrastructure.database.repository.GenreRepository
import io.restassured.RestAssured
import io.restassured.common.mapper.TypeRef
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.http.HttpStatus

class GenreFindEndpointTest(private val genreRepository: GenreRepository) : TestContainerBaseConfiguration() {

    @BeforeEach
    fun cleanUp() {
        clearData()
        names().forEach {
            val genre = genreRepository.save(GenreEntityFactory.build(name = it.key))
            it.value.forEach { subGenre ->
                genreRepository.save(GenreEntityFactory.build(parent = genre, name = subGenre))
            }
        }
    }

    @Test
    fun `get all genres default successfully`() {
        val response = RestAssured.given()
            .`when`()
            .get("/genres")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(object : TypeRef<PageResponse<GenreResponse>>() {})

        Assertions.assertEquals(1, response.page)
        Assertions.assertEquals(1, response.totalPages)
        Assertions.assertEquals(10, response.content.size)
        names().toSortedMap().entries.forEachIndexed { index, entry ->
            Assertions.assertEquals(entry.key, response.content[index].name)
            entry.value.sorted().forEachIndexed { subIndex, subGenre ->
                Assertions.assertEquals(subGenre, response.content[index].subGenres?.get(subIndex)?.name)
            }
        }
    }

    @Test
    fun `get all genres descendent successfully`() {
        val response = RestAssured.given()
            .queryParam("directionAsc", false)
            .`when`()
            .get("/genres")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(object : TypeRef<PageResponse<GenreResponse>>() {})

        names().toSortedMap().reversed().entries.forEachIndexed { index, entry ->
            Assertions.assertEquals(entry.key, response.content[index].name)
            entry.value.sorted().forEachIndexed { subIndex, subGenre ->
                Assertions.assertEquals(subGenre, response.content[index].subGenres?.get(subIndex)?.name)
            }
        }
    }

    @Test
    fun `get genres filtering by name successfully`() {
        val response = RestAssured.given()
            .queryParam("name", "xpto")
            .`when`()
            .get("/genres")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(object : TypeRef<PageResponse<GenreResponse>>() {})

        Assertions.assertEquals(1, response.page)
        Assertions.assertEquals(1, response.totalPages)
        Assertions.assertEquals(4, response.content.size)
        names().filter {
            it.key.contains("xpto", true) ||
            it.value.any { sub -> sub.contains("xpto", true) }
        }.toSortedMap()
            .entries.forEachIndexed { index, entry ->
                Assertions.assertEquals(entry.key, response.content[index].name)
                entry.value.sorted().forEachIndexed { subIndex, subGenre ->
                    Assertions.assertEquals(subGenre, response.content[index].subGenres?.get(subIndex)?.name)
                }
            }
    }

    @Test
    fun `get second page genres size 4 order by name successfully`() {
        val response = RestAssured.given()
            .queryParam("page", "2")
            .queryParam("size", "4")
            .queryParam("order", "ID")
            .`when`()
            .get("/genres")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(object : TypeRef<PageResponse<GenreResponse>>() {})

        Assertions.assertEquals(2, response.page)
        Assertions.assertEquals(3, response.totalPages)
        Assertions.assertEquals(4, response.content.size)
        names().toSortedMap().keys.toList().subList(4, 7).forEachIndexed { index, key ->
            Assertions.assertEquals(key, response.content[index].name)
            names()[key]?.sorted()?.forEachIndexed { subIndex, subGenre ->
                Assertions.assertEquals(subGenre, response.content[index].subGenres?.get(subIndex)?.name)
            }
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["0", "-1"])
    fun `get genres negative or zero page`(page: String) {

        val response = RestAssured.given()
            .queryParam("page", page)
            .`when`()
            .get("/genres")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }

    @ParameterizedTest
    @ValueSource(strings = ["0", "-1", "300", "1000"])
    fun `get genres wrong page size`(pageSize: String) {
        val response = RestAssured.given()
            .queryParam("size", pageSize)
            .`when`()
            .get("/genres")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }

    companion object {
        private fun names(): Map<String, List<String>> =
            mapOf(
                Pair("02 Name Two", listOf("02.02 Name Two.two", "02.01 Name Two.one")),
                Pair("03 Name Three", listOf()),
                Pair("01 Name One", listOf("01.03 Name xpto One.three", "01.01 Name One.one", "01.03 Name One.two")),
                Pair("05 Name Five", listOf()),
                Pair(
                    "06 Name Six XPTO more",
                    listOf("06.01 Name Six.one", "06.03 Name XPTO Six.two", "06.02 Name Six.two")
                ),
                Pair("09 Name Nine", listOf()),
                Pair("04 Name Four Xpto", listOf()),
                Pair("10 Name Ten", listOf("10.01 Name Ten.one", "10.03 Name Ten.two")),
                Pair("08 Name Eight xpTO ", listOf()),
                Pair("07 Name Seven", listOf("07.01 Name Seven.one", "07.03 Name Seven.two"))
            )
    }
}
