package at.tugraz.vaccinationpassport.backend.api.data

data class LoginDetails (
    val passportNumber: String,
    val password: String
)