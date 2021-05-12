package at.tugraz.vaccinationpassport.backend.api

import at.tugraz.vaccinationpassport.Vaccination
import at.tugraz.vaccinationpassport.backend.api.data.LoginDetails
import at.tugraz.vaccinationpassport.backend.api.data.ProfileData
import retrofit2.Response

class Repository {
    suspend fun pushLogin(loginDetails: LoginDetails): Response<Unit> {
        return RetrofitInstance.api.pushLogin(loginDetails)
    }

    suspend fun getProfile(): Response<ProfileData>
    {
        // return dummy data for now -> TODO replace this with data from server
        return Response.success( ProfileData("Max Mustermann", 43, 12345678, 5))
    }

    fun getVaccineList(authToken: String): Response<List<Vaccination>> {
        return Response.success(List<Vaccination>(5) { index ->
            Vaccination("Vaccine Nr. $index", "2021-0${index + 1}-0${index + 1}")
        })
    }
}