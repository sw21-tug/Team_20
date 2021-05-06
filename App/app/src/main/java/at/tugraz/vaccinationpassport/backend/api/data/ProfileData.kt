package at.tugraz.vaccinationpassport.backend.api.data

data class ProfileData (
    val name: String,
    val age: Int,
    val passportNumber: Int,
    val amountOfVaccines: Int
)
