package at.tugraz.vaccinationpassportserver.security

import at.tugraz.vaccinationpassportserver.VaccinationPassportServerApplication
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus


@SpringBootTest(classes = [VaccinationPassportServerApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MyRestControllerTest {
    @LocalServerPort
    private val port = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private fun callExampleAPI(entity: HttpEntity<String>) =
            restTemplate.exchange(
                    "http://localhost:$port/example",
                    HttpMethod.GET, entity, String::class.java)

    @Test
    fun exampleAPIwithoutAuthorizationHeader_shouldBeForbidden() {
        val expectedStatusCode = HttpStatus.FORBIDDEN
        val headers = HttpHeaders()
        val entity = HttpEntity<String>(null, headers)
        val response = callExampleAPI(entity)
        assertEquals(expectedStatusCode, response.statusCode)
    }

    @Test
    fun exampleAPIwithoutBearer_shouldBeForbidden() {
        val expectedStatusCode = HttpStatus.FORBIDDEN
        val headers = HttpHeaders()
        headers.add("Authorization", "Some_value")
        val entity = HttpEntity<String>(null, headers)
        val response = callExampleAPI(entity)
        assertEquals(expectedStatusCode, response.statusCode)
    }

    @Test
    fun exampleAPIwithoutValidBearer_shouldBeForbidden() {
        val expectedStatusCode = HttpStatus.FORBIDDEN
        val headers = HttpHeaders()
        headers.add("Authorization", "Bearer Some_value")
        val entity = HttpEntity<String>(null, headers)
        val response = callExampleAPI(entity)
        assertEquals(expectedStatusCode, response.statusCode)
    }

    @Test
    fun loginWithInvalidCredentials_shouldBeForbitten() {
        val expectedStatusCode = HttpStatus.FORBIDDEN
        val headers = HttpHeaders()
        val body = "{\n" +
                "    \"username\":\"admin\",\n" +
                "    \"password\":\"wrong\"\n" +
                "}"
        val entity = HttpEntity<String>(body, headers)
        val response = restTemplate.exchange(
                "http://localhost:$port/login",
                HttpMethod.POST, entity, String::class.java)
        assertEquals(expectedStatusCode, response.statusCode)
    }

    @Test
    fun loginWithValidCredentials_shouldBeOK() {
        val expectedStatusCode = HttpStatus.OK
        val headers = HttpHeaders()
        val body = "{\n" +
                "    \"username\":\"admin\",\n" +
                "    \"password\":\"password\"\n" +
                "}"
        val entity = HttpEntity<String>(body, headers)
        val response = restTemplate.exchange(
                "http://localhost:$port/login",
                HttpMethod.POST, entity, String::class.java)
        assertEquals(expectedStatusCode, response.statusCode)
        val authentication = response.headers.getOrEmpty("Authorization")
        assertTrue(authentication.isNotEmpty())
    }

    @Test
    fun exampleAPIwithValidBearer_shouldBeOK() {

        // Preparation
        val expectedStatusCodePrep = HttpStatus.OK
        val headersPrep = HttpHeaders()
        val bodyPrep = "{\n" +
                "    \"username\":\"admin\",\n" +
                "    \"password\":\"password\"\n" +
                "}"
        val entityPrep = HttpEntity<String>(bodyPrep, headersPrep)
        val responsePrep = restTemplate.exchange(
                "http://localhost:$port/login",
                HttpMethod.POST, entityPrep, String::class.java)
        assertEquals(expectedStatusCodePrep, responsePrep.statusCode)
        val authentication = responsePrep.headers.getOrEmpty("Authorization")
        assertTrue(authentication.isNotEmpty())
        val bearer = authentication[0]

        // Test
        val expectedStatusCode = HttpStatus.OK
        val headers = HttpHeaders()
        headers.add("Authorization", bearer)
        val entity = HttpEntity<String>(null, headers)
        val response = callExampleAPI(entity)
        assertEquals(expectedStatusCode, response.statusCode)
    }
}