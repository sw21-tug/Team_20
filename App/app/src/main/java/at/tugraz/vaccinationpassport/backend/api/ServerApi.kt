package at.tugraz.vaccinationpassport.backend.api

import at.tugraz.vaccinationpassport.backend.api.data.LoginDetails
import at.tugraz.vaccinationpassport.backend.api.data.ProfileData
import at.tugraz.vaccinationpassport.backend.api.data.VaccineDetails
import retrofit2.Response
import retrofit2.http.*

interface ServerApi {

    @POST("/login")
    suspend fun pushLogin(
        @Body loginDetails: LoginDetails
    ): Response<Unit>

    @POST("/users/{passportNumber}/add-vaccine")
    suspend fun pushAddVaccine(
        @Path("passportNumber") passportNumber: String,
        @Body vaccineDetails: VaccineDetails,
        @Header("Authorization") authToken: String
    ): Response<Boolean>

    @GET("/users/{passportNumber}")
    suspend fun getUserProfile(
        @Path("passportNumber") passportNumber: String,
        @Header("Authorization") authToken: String
    ): Response<ProfileData>

}