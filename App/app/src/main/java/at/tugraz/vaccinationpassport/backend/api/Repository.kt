package at.tugraz.vaccinationpassport.backend.api

import at.tugraz.vaccinationpassport.backend.api.data.LoginDetails
import retrofit2.Response

class Repository {
    suspend fun pushLogin(loginDetails: LoginDetails): Response<Unit> {
        return RetrofitInstance.api.pushLogin(loginDetails)
    }
}