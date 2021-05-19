package at.tugraz.vaccinationpassport

import at.tugraz.vaccinationpassport.backend.Server
import at.tugraz.vaccinationpassport.backend.api.Repository
import at.tugraz.vaccinationpassport.backend.api.RetrofitInstance
import at.tugraz.vaccinationpassport.backend.api.data.LoginDetails
import at.tugraz.vaccinationpassport.backend.api.data.ProfileData
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

class ProfileTest {
    var requestSuccessful: Boolean = false
    var requestFailed: Boolean = false
    var isProfileValid: Boolean = false
    private val testDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        requestSuccessful = false
        requestFailed = false
        isProfileValid = false

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

        server.onProfileReceived = { profile ->
            requestSuccessful = true
            testProfileValid(profile)
        }
        server.onProfileRequestFailed = {requestFailed = true}
        server.getProfile()
        sleep(500)

        assertTrue(requestSuccessful)
        assertTrue(isProfileValid)
        assertFalse(requestFailed)
    }

    @Test
    fun getProfileWithInvalidCredentials() {
        val server = Server(Repository())
        val loginDetails = LoginDetails("12345678", "wrong!!!")
        server.login(loginDetails)

        server.onProfileReceived = {_ -> requestSuccessful = true}
        server.onProfileRequestFailed = { requestFailed = true }
        server.getProfile()
        sleep(500)

        assertFalse(requestSuccessful)
        assertTrue(requestFailed)
    }

    @Test
    fun getProfileWithoutLogin() = runBlockingTest {
        launch {
            val server = Server(Repository())

            server.onProfileReceived = {_ -> requestSuccessful = true}
            server.onProfileRequestFailed = { requestFailed = true }
            requestSuccessful = false
            server.getProfile()
            sleep(500)

            assertFalse(requestSuccessful)
            assertTrue(requestFailed)
        }
    }

    private fun testProfileValid(profile: ProfileData) {
        isProfileValid = profile.name == "Max Mustermann" &&
                profile.age == 30 &&
                profile.passportNumber == 12345678 &&
                profile.amountOfVaccines == 4
    }
}