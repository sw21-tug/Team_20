package at.tugraz.vaccinationpassport

import at.tugraz.vaccinationpassport.backend.Server
import at.tugraz.vaccinationpassport.backend.api.Repository
import at.tugraz.vaccinationpassport.backend.api.RetrofitInstance
import at.tugraz.vaccinationpassport.backend.api.data.LoginDetails
import at.tugraz.vaccinationpassport.backend.api.data.VaccineDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.lang.Thread.sleep

class AddVaccinesTest {
    var isVaccineAdded: Boolean = false
    var vaccineListReceived: Boolean = false
    var isVaccineListValid: Boolean = false
    private val testDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        isVaccineAdded = false
        vaccineListReceived = false
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
        System.out.println("Started")
        var server = Server(Repository())
        val loginDetailsDoctor = LoginDetails("11223344", "password")
        server.login(loginDetailsDoctor)
        sleep(500)

        server.onVaccineAdded = {
            isVaccineAdded = true
        }
        server.onVaccineAddingFailed = {
            isVaccineAdded = false
        }

        val vaccination = Vaccination("Common Cold", "26.05.2021 11:11")
        val vacDetails = VaccineDetails("12345678", vaccination)
        server.addVaccine(vacDetails)
        sleep(500)

        server = Server(Repository())
        val loginDetailsUser = LoginDetails("12345678", "password")
        server.login(loginDetailsUser)
        sleep(500)

        server.onVaccineListReceived = { vaccine_list ->
            vaccineListReceived = true
            testVaccineList(vaccine_list, vaccination)
        }
        server.onVaccineListRequestFailed = { vaccineListReceived = false }
        server.getVaccineList()
        sleep(500)

        assertTrue(isVaccineAdded)
        assertTrue(vaccineListReceived)
        assertTrue(isVaccineListValid)
    }

    fun testVaccineList(vaccine_list : List<Vaccination>, vaccination: Vaccination ){
        isVaccineListValid = vaccine_list.contains(vaccination)
    }
}