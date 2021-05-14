package at.tugraz.vaccinationpassportserver.security

import at.tugraz.vaccinationpassportserver.VaccinationPassportServerApplication
import at.tugraz.vaccinationpassportserver.user.User
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
class UserTest {
    @LocalServerPort
    private val port = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private fun callUserAPI(entity: HttpEntity<String>, passportNumber:String) =
        restTemplate.exchange(
            "http://localhost:$port/users",
            HttpMethod.GET, entity, String::class.java)

    @Test
    fun getOwnUserdetails_shouldBeOK() {

        // Preparation
        val expectedStatusCodePrep = HttpStatus.OK
        val headersPrep = HttpHeaders()
        val bodyPrep = "{\n" +
                "    \"passportNumber\":\"12345678\",\n" +
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
        val response = restTemplate.exchange(
            "http://localhost:$port/users/12345678",
            HttpMethod.GET, entity, User::class.java)
        assertEquals(expectedStatusCode, response.statusCode)

        assertEquals(30, response.body?.getAge())
        assertEquals("Max Mustermann", response.body?.getName())
        assertEquals("12345678", response.body?.getPassportNumber())
        assertEquals(4, response.body?.getNrOfVaccines())
    }
}