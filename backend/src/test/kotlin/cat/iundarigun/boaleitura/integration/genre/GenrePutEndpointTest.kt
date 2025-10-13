package cat.iundarigun.boaleitura.integration.genre

import cat.iundarigun.boaleitura.configuration.FakerConfiguration
import cat.iundarigun.boaleitura.configuration.TestContainerBaseConfiguration
import cat.iundarigun.boaleitura.domain.request.GenreRequest
import cat.iundarigun.boaleitura.domain.response.ErrorResponse
import cat.iundarigun.boaleitura.domain.response.GenreResponse
import cat.iundarigun.boaleitura.factory.GenreEntityFactory
import cat.iundarigun.boaleitura.factory.GenreRequestFactory
import cat.iundarigun.boaleitura.infrastructure.database.repository.GenreRepository
import io.restassured.RestAssured
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

class GenrePutEndpointTest(private val genreRepository: GenreRepository) : TestContainerBaseConfiguration() {

    @Test
    fun `update genre without parent successfully`() {
        val genre = genreRepository.save(GenreEntityFactory.build())
        val request = GenreRequestFactory.build()
        val count = genreRepository.count()

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .given()
            .pathParam("id", genre.id)
            .`when`()
            .put("/genres/{id}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(GenreResponse::class.java)

        Assertions.assertEquals(count, genreRepository.count())
        Assertions.assertEquals(request.name, response.name)
        val updated = genreRepository.findById(genre.id).orElseThrow()
        Assertions.assertEquals(request.name, updated.name)
        Assertions.assertEquals(genre.version + 1, updated.version)
        Assertions.assertNull(updated.parent)
    }

    @Test
    fun `update genre with parent successfully`() {
        val parent = genreRepository.save(GenreEntityFactory.build())
        val genre = genreRepository.save(GenreEntityFactory.build(parent))
        val request = GenreRequest(
            name = genre.name + FakerConfiguration.FAKER.number().randomNumber(),
            parentGenreId = parent.id)
        val count = genreRepository.count()

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .given()
            .pathParam("id", genre.id)
            .`when`()
            .put("/genres/{id}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(GenreResponse::class.java)

        Assertions.assertEquals(count, genreRepository.count())
        Assertions.assertEquals(request.name, response.name)
        Assertions.assertEquals(request.parentGenreId, response.parent?.id)
        val updated = genreRepository.findById(genre.id).orElseThrow()
        Assertions.assertEquals(request.name, updated.name)
        Assertions.assertEquals(genre.version + 1, updated.version)
        Assertions.assertNotNull(updated.parent)
        Assertions.assertEquals(request.parentGenreId, updated.parent?.id)
    }

    @Test
    fun `update genre new parent successfully`() {
        val currentParent = genreRepository.save(GenreEntityFactory.build())
        val genre = genreRepository.save(GenreEntityFactory.build(currentParent))
        val newParent = genreRepository.save(GenreEntityFactory.build())
        val request = GenreRequest(
            name = genre.name + FakerConfiguration.FAKER.number().randomNumber(),
            parentGenreId = newParent.id)
        val count = genreRepository.count()

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .given()
            .pathParam("id", genre.id)
            .`when`()
            .put("/genres/{id}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .`as`(GenreResponse::class.java)

        Assertions.assertEquals(count, genreRepository.count())
        Assertions.assertEquals(request.name, response.name)
        Assertions.assertEquals(request.parentGenreId, response.parent?.id)
        val updated = genreRepository.findById(genre.id).orElseThrow()
        Assertions.assertEquals(request.name, updated.name)
        Assertions.assertEquals(genre.version + 1, updated.version)
        Assertions.assertNotNull(updated.parent)
        Assertions.assertEquals(request.parentGenreId, updated.parent?.id)
    }

    @Test
    fun `update genre with existing name`() {
        val existing = genreRepository.save(GenreEntityFactory.build())
        val genre = genreRepository.save(GenreEntityFactory.build())
        val request = GenreRequest(name = existing.name)

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .given()
            .pathParam("id", genre.id)
            .`when`()
            .put("/genres/{id}")
            .then()
            .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.code)
    }

    @Test
    fun `update genre with non-existing parent`() {
        val genre = genreRepository.save(GenreEntityFactory.build())
        val request = GenreRequest(
            name = genre.name,
            parentGenreId = FakerConfiguration.FAKER.number().numberBetween(10_000L, 99_999L)
        )

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .given()
            .pathParam("id", genre.id)
            .`when`()
            .put("/genres/{id}")
            .then()
            .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.code)
    }

    @Test
    fun `update genre with circular parent`() {
        val genre = genreRepository.save(GenreEntityFactory.build())
        val subGenre = genreRepository.save(GenreEntityFactory.build(genre))
        val request = GenreRequest(
            name = genre.name,
            parentGenreId = subGenre.id
        )

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .given()
            .pathParam("id", genre.id)
            .`when`()
            .put("/genres/{id}")
            .then()
            .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.code)
    }

    @Test
    fun `update genre with id does not exist`() {
        val request = GenreRequestFactory.build()

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .given()
            .pathParam("id", FakerConfiguration.FAKER.number().numberBetween(1_000, 9_999))
            .`when`()
            .put("/genres/{id}")
            .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.code)
    }

    @Test
    fun `update genre with parentId does not exist`() {
        val genre = genreRepository.save(GenreEntityFactory.build())
        val request = GenreRequestFactory.build(
            FakerConfiguration.FAKER.number().numberBetween(1_000L, 9_999L)
        )

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .given()
            .pathParam("id", genre.id)
            .`when`()
            .put("/genres/{id}")
            .then()
            .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.code)
    }

    @Test
    fun `update genre without name`() {
        val genre = genreRepository.save(GenreEntityFactory.build())

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("""{"parentGenreId": 10}""")
            .given()
            .pathParam("id", genre.id)
            .`when`()
            .put("/genres/{id}")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }

    @Test
    fun `update genre with non-number parentId`() {
        val genre = genreRepository.save(GenreEntityFactory.build())

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body("""{"name": "Genre name", "parentGenreId": "BAD GENDER"}""")
            .given()
            .pathParam("id", genre.id)
            .`when`()
            .put("/genres/{id}")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }

    @Test
    fun `update genre with small name`() {
        val genre = genreRepository.save(GenreEntityFactory.build())
        val request = GenreRequest(name = "SM")

        val response = RestAssured.given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .pathParam("id", genre.id)
            .`when`()
            .`when`()
            .put("/genres/{id}")
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .extract()
            .`as`(ErrorResponse::class.java)

        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), response.code)
    }
}
