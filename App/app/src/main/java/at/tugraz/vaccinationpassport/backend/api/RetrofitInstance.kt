package at.tugraz.vaccinationpassport.backend.api

import at.tugraz.vaccinationpassport.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val api: ServerApi by lazy {
        retrofit.create(ServerApi::class.java)
    }
}