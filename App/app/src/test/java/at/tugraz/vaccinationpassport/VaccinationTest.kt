package at.tugraz.vaccinationpassport

import junit.framework.TestCase

class VaccinationTest : TestCase() {

    fun testGetName() {
        val vaccination = Vaccination("Covid", "31-12-2018")

        assertEquals("Covid", vaccination.name)
    }

    fun testGetDate() {
        val vaccination = Vaccination("Covid", "31-12-2018")

        assertEquals("31-12-2018", vaccination.date)
    }
}