package at.tugraz.vaccinationpassport

import org.junit.Test

import org.junit.Assert.*

class UserTest {

    @Test
    fun addVaccinesTest() {
        val user = User("John Doe", 27)
        user.addVaccination(Vaccination("Covid", "12-02-2018"))

        assertEquals(1, user.vaccines.size)
        assertEquals("Covid", user.vaccines[0].name)
        assertEquals("12-02-2018", user.vaccines[0].date)

        var vaccines = mutableListOf<Vaccination>()
        vaccines.add(Vaccination("Malaria", "12-02-2018"))
        vaccines.add(Vaccination("Hepatitis A", "12-02-2018"))
        vaccines.add(Vaccination("Hepatitis B", "12-02-2018"))

        user.addVaccination(vaccines)

        var matches = 0
        for(vaccine in vaccines)
        {
            for(user_vaccine in user.vaccines)
            {
                if(user_vaccine.name == vaccine.name &&
                    user_vaccine.date == vaccine.date)
                {
                    matches += 1
                }
            }
        }
        assertEquals(vaccines.size, matches)
    }
}