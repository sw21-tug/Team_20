package at.tugraz.vaccinationpassport

class User(val name : String, val age: Int) {
    val vaccines : MutableList<Vaccination> = mutableListOf()

    /*
    * Add a single vaccination
     */
    fun addVaccination(vaccination: Vaccination)
    {
        vaccines.add(vaccination)
    }

    /*
    * Add all vaccinations in the collection
     */
    fun addVaccination(vaccinations: Collection<Vaccination>)
    {
        vaccines.addAll(vaccinations)
    }

    /*
    * Remove a single vaccination
     */
    fun removeVaccination(vaccination: Vaccination)
    {
        vaccines.remove(vaccination)
    }

}