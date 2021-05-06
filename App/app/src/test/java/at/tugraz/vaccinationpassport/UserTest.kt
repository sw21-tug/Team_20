package at.tugraz.vaccinationpassport

import org.junit.Test

import org.junit.Assert.*

class UserTest {

    @Test
    fun getVaccines() {
        val user = User("John Doe", 27)
        user.addVaccination(Vaccination("Covid", "31-12-2018"))

        assertEquals(1, user.vaccines.size)
    }

    @Test
    fun addVaccination() {
    }

    @Test
    fun testAddVaccination() {
    }

    @Test
    fun removeVaccination() {
    }
}