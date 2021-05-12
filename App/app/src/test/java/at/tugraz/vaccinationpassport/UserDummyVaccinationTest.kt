package at.tugraz.vaccinationpassport

import at.tugraz.vaccinationpassport.backend.Server
import at.tugraz.vaccinationpassport.backend.api.Repository
import at.tugraz.vaccinationpassport.backend.api.RetrofitInstance
import at.tugraz.vaccinationpassport.backend.api.data.LoginDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.lang.Thread.sleep

class UserDummyVaccinationTest {
    var requestSuccessful: Boolean = false
    var requestFailed: Boolean = false
    var isVaccineListValid: Boolean = false
    private val testDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        requestSuccessful = false
        requestFailed = false
        isVaccineListValid = false

        RetrofitInstance.url = "http://127.0.0.1:8080"

        // provide the scope explicitly, in this example using a constructor parameter
        Dispatchers.setMain(testDispatcher)
    }

    @ExperimentalCoroutinesApi
    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun getProfileWithValidCredentials() {
        val server = Server(Repository())
        val loginDetails = LoginDetails("12345678", "password")
        server.login(loginDetails)
        sleep(500)

        server.onVaccineListReceived = { vaccine_list ->
            requestSuccessful = true
            testVaccineListValid(vaccine_list)
        }
        server.onVaccineListRequestFailed = {requestFailed = true}
        server.getVaccineList()
        sleep(500)

        assertTrue(requestSuccessful)
        assertTrue(isVaccineListValid)
        assertFalse(requestFailed)
    }

    @Test
    fun getProfileWithInvalidCredentials() {
        val server = Server(Repository())
        val loginDetails = LoginDetails("12345678", "wrong!!!")
        server.login(loginDetails)

        server.onVaccineListReceived = {_ -> requestSuccessful = true}
        server.onVaccineListRequestFailed = { requestFailed = true }
        server.getVaccineList()
        sleep(500)

        assertFalse(requestSuccessful)
        assertTrue(requestFailed)
    }

    @Test
    fun getProfileWithoutLogin() = runBlockingTest {
        launch {
            val server = Server(Repository())

            server.onVaccineListReceived = {_ -> requestSuccessful = true}
            server.onVaccineListRequestFailed = { requestFailed = true }
            requestSuccessful = false
            server.getVaccineList()
            sleep(500)

            assertFalse(requestSuccessful)
            assertTrue(requestFailed)
        }
    }

    private fun testVaccineListValid(vaccine_list: List<Vaccination>) {
        isVaccineListValid = true

        for (i in vaccine_list.indices)
        {
            if(vaccine_list[i].name != "Vaccine Nr. $i" ||
                vaccine_list[i].date != "2021-0${i + 1}-0${i + 1}")
            {
                isVaccineListValid = false
                return;
            }
        }
    }
}