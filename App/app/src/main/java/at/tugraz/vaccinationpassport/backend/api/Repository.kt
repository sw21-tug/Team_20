package at.tugraz.vaccinationpassport.backend.api

import android.os.Parcel
import android.os.Parcelable
import at.tugraz.vaccinationpassport.Vaccination
import at.tugraz.vaccinationpassport.backend.api.data.LoginDetails
import at.tugraz.vaccinationpassport.backend.api.data.ProfileData
import at.tugraz.vaccinationpassport.backend.api.data.VaccineDetails
import retrofit2.Response

class Repository() : Parcelable {

    suspend fun pushLogin(loginDetails: LoginDetails): Response<Unit> {
        return RetrofitInstance.api.pushLogin(loginDetails)
    }

    suspend fun getProfile(passportNumber: String, authToken: String): Response<ProfileData> {
        return RetrofitInstance.api.getUserProfile(passportNumber, authToken)
    }

    fun getVaccineList(authToken: String): Response<List<Vaccination>> {
        return Response.success(List<Vaccination>(5) { index ->
            Vaccination("Vaccine Nr. $index", "2021-0${index + 1}-0${index + 1}")
        })
    }

    suspend fun addVaccineDetails(passportNumber: String, vacDetails: VaccineDetails, authToken: String): Response<Boolean> {
        return RetrofitInstance.api.pushAddVaccine(passportNumber, vacDetails, authToken)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Repository> {
        override fun createFromParcel(parcel: Parcel): Repository {
            return Repository()
        }

        override fun newArray(size: Int): Array<Repository?> {
            return arrayOfNulls(size)
        }
    }
}