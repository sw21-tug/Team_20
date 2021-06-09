package at.tugraz.vaccinationpassport.backend.api.data

import at.tugraz.vaccinationpassport.Vaccination

data class VaccineDetails(val passportNumber: String, val vaccine: Vaccination)
