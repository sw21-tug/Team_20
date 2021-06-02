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

class AddVaccinesTest {
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
    fun loginDoctorAddVac() {
        var server = Server(Repository())
        val loginDoctorDetails = LoginDetails("11223344", "password")
        server.login(loginDetails)
        Thread.sleep(500)

        server.onVaccineAdded = {
            requestSuccessful = true
        }
        server.onVaccineAddingFailed = { requestFailed = true }

        val vaccination = Vaccination("Common Cold", "26.05.2021 11:11")
        val vacDetails = VaccineDetails("12345678", vaccination)
        server.addVaccine(vacDetails)
        Thread.sleep(500)

        server = Server(Repository())
        val loginDetails = LoginDetails("12345678", "password")
        server.login(loginDetails)
        Thread.sleep(500)

        server.onVaccineListReceived = { vaccine_list ->
            requestSuccessful = true
            testVaccineList(vaccine_list, vaccination)
        }
        server.onVaccineListRequestFailed = { requestFailed = true }
        server.getVaccineList()
        sleep(500)

        assertTrue(requestSuccessful)
        assertTrue(isVaccineListValid)
        assertFalse(requestFailed)
    }

    fun testVaccineList(vaccine_list : List<Vaccination>, vaccination: Vaccination ){
        isVaccineListValid = vaccine_list.contains(vaccination)
    }
}