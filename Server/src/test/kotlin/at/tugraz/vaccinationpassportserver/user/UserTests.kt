package at.tugraz.vaccinationpassportserver.security

import at.tugraz.vaccinationpassportserver.VaccinationPassportServerApplication
import at.tugraz.vaccinationpassportserver.user.User
import at.tugraz.vaccinationpassportserver.user.Vaccine
import at.tugraz.vaccinationpassportserver.user.VaccineHolder
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

    @Test
    fun getOtherUserdetails_shouldBeForbidden() {

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
        val expectedStatusCode = HttpStatus.FORBIDDEN
        val headers = HttpHeaders()
        headers.add("Authorization", bearer)
        val entity = HttpEntity<String>(null, headers)
        val response = restTemplate.exchange(
            "http://localhost:$port/users/87654321",
            HttpMethod.GET, entity, User::class.java)
        assertEquals(expectedStatusCode, response.statusCode)
    }

    @Test
    fun getOwnUserdetails_shouldNotSendPassword() {

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

        assertEquals(null, response.body?.getPassword())
    }

    @Test
    fun getOwnVaccineList_shouldBeOK() {

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
                "http://localhost:$port/users/12345678/vaccines",
                HttpMethod.GET, entity, MutableList::class.java)

        assertEquals(expectedStatusCode, response.statusCode)
        assertEquals(Vaccine("Covid", "21-01-2021"),
                Vaccine.from(response.body?.get(0) as Map<String, String>))
        assertEquals(Vaccine("FSME", "29-05-2015"),
                Vaccine.from(response.body?.get(1) as Map<String, String>))
    }

    fun loginAsUser_shouldBeOK() : String?
    {
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

        return authentication[0]
    }

    fun userHasVaccine(vaccine: Vaccine, bearer: String?)
    {
        val expectedStatusCode = HttpStatus.OK
        val headers = HttpHeaders()
        headers.add("Authorization", bearer)
        val entity = HttpEntity<String>(null, headers)
        val response = restTemplate.exchange(
                "http://localhost:$port/users/12345678/vaccines",
                HttpMethod.GET, entity, MutableList::class.java)

        var found : Boolean = false

        for (b in response.getBody()!!)
        {
            val v = Vaccine.from(b as Map<String, String>)

            if(v.name == vaccine.name && v.date == vaccine.date)
            {
                found = true
            }
        }

        assertEquals(found, true)
    }

    @Test
    fun AddToVaccineList_shouldBeOK() {
        //login as doctor
        val expectedStatusCodePrep = HttpStatus.OK
        val headersPrep = HttpHeaders()
        val bodyPrep = "{\n" +
                "    \"passportNumber\":\"11223344\",\n" +
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

        //add vaccine to user
        val expectedStatusCode = HttpStatus.OK
        val headers = HttpHeaders()
        headers.add("Authorization", bearer)
        /*val body = "{\n" +
                "    \"passportNumber\":\"12345678\",\n"
                "    \"vaccine\":\n" +
                "    {\n" +
                "        \"name\":\"Common Cold\",\n" +
                "        \"date\":\"26.05.2021\"\n" +
                "    }\n" +
                "}"*/
        val body = VaccineHolder("12345678", Vaccine("Common Cold", "26.05.2021"))
        val entity = HttpEntity<VaccineHolder>(body, headers)
        //val entity = HttpEntity<String>(body, headers)
        val response = restTemplate.exchange(
                "http://localhost:$port/users/11223344/add-vaccine",
                HttpMethod.POST, entity, Boolean::class.java)
        assertEquals(expectedStatusCode, response.statusCode)

        //login as user
        val userBearer = loginAsUser_shouldBeOK()

        //check if user has vaccine
        userHasVaccine(Vaccine("Common Cold", "26.05.2021"), userBearer)
    }
}