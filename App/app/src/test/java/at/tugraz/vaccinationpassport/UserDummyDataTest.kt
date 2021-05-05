package at.tugraz.vaccinationpassport

import at.tugraz.vaccinationpassport.backend.Server
import at.tugraz.vaccinationpassport.backend.api.Repository
import at.tugraz.vaccinationpassport.backend.api.data.LoginDetails
import at.tugraz.vaccinationpassport.backend.api.data.ProfileData
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import java.lang.Thread.sleep

class UserDummyData {
    var calledResponseFunction: Boolean = false

    @Test
    fun getProfileWithValidCredentials() {
        val server = Server(Repository())
        val loginDetails = LoginDetails("12345678", "password")
        server.login(loginDetails)

        server.onProfileReceived = ::testProfileValid
        server.onProfileRequestFailed = ::fail
        calledResponseFunction = false
        server.getProfile()

        sleep(500)
        assertTrue(calledResponseFunction)
    }

    @Test
    fun getProfileWithInvalidCredentials() {
        val server = Server(Repository())
        val loginDetails = LoginDetails("12345678", "wrong!!!")
        server.login(loginDetails)

        server.onProfileReceived = ::fail
        server.onProfileRequestFailed = ::success
        calledResponseFunction = false
        server.getProfile()

        sleep(500)
        assertTrue(calledResponseFunction)
    }

    @Test
    fun getProfileWithoutLogin() {
        val server = Server(Repository())

        server.onProfileReceived = ::fail
        server.onProfileRequestFailed = ::success
        calledResponseFunction = false
        server.getProfile()

        sleep(500)
        assertTrue(calledResponseFunction)
    }

    private fun testProfileValid(profile: ProfileData) {
        calledResponseFunction = true
        assertEquals(profile.name, "Max Mustermann")
        assertEquals(profile.age, 43)
        assertEquals(profile.passportNumber, 12345678)
        assertEquals(profile.amountOfVaccines, 5)
    }

    private fun fail() {
        calledResponseFunction = true
        Assert.fail()
    }

    private fun fail(profile: ProfileData) {
        calledResponseFunction = true
        Assert.fail()
    }

    private fun success() {
        calledResponseFunction = true
    }
}