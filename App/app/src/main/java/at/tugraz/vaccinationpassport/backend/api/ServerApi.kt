package at.tugraz.vaccinationpassport.backend.api

import at.tugraz.vaccinationpassport.backend.api.data.LoginDetails
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ServerApi {

    @POST("/login")
    suspend fun pushLogin(
        @Body loginDetails: LoginDetails
    ): Response<Unit>


}