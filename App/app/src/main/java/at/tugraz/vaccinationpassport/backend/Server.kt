package at.tugraz.vaccinationpassport.backend


import android.os.Parcel
import android.os.Parcelable
import at.tugraz.vaccinationpassport.Vaccination
import at.tugraz.vaccinationpassport.backend.api.Repository
import at.tugraz.vaccinationpassport.backend.api.data.LoginDetails
import at.tugraz.vaccinationpassport.backend.api.data.ProfileData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception


class Server(private val repository: Repository) : Parcelable {
    private var authToken: String? = null
    private var passportNumber: String? = null

    var onLoginSuccessful: (Boolean) -> Unit = {}
    var onLoginFailed: (Boolean) -> Unit = {}

    var onProfileReceived: (ProfileData) -> Unit = {}
    var onProfileRequestFailed: () -> Unit = {}

    var onVaccineListReceived: (List<Vaccination>) -> Unit = {}
    var onVaccineListRequestFailed: () -> Unit = {}

    fun login(loginDetails: LoginDetails) {
        passportNumber = loginDetails.passportNumber

        GlobalScope.launch {
            try {
                val response = repository.pushLogin(loginDetails)

                GlobalScope.launch(Dispatchers.Main) {
                    handleLoginResponse(response)
                }
            } catch (e: Exception) {
                GlobalScope.launch(Dispatchers.Main) {
                    onLoginFailed(false)
                }
            }
        }
    }

    private fun handleLoginResponse(response: Response<Unit>) {
        if (!response.isSuccessful || response.code() != 200 || response.headers()["Authorization"] == null) {
            onLoginFailed(true)
            return
        }

        authToken = response.headers()["Authorization"]!!
        val isDoctor = response.headers()["isDoctor"].toBoolean()

        onLoginSuccessful(isDoctor)
    }

    private fun handleGetProfileResponse(response: Response<ProfileData>) {
        if (!response.isSuccessful) {
            onProfileRequestFailed()
            return
        }

        onProfileReceived(response.body()!!)
    }

    fun getProfile() {
        // check if passportNumber and authentication token exist
        if(authToken == null || passportNumber == null)
        {
            onProfileRequestFailed()
            return
        }
        GlobalScope.launch {
            try {
                val response = repository.getProfile(passportNumber!!, authToken!!)

                GlobalScope.launch(Dispatchers.Main) {
                    handleGetProfileResponse(response)
                }
            } catch (e: Exception) {
                GlobalScope.launch(Dispatchers.Main) {
                    onProfileRequestFailed()
                }
            }
        }
    }

    fun getVaccineList() {

        // check if authentication token is valid
        if(authToken == null)
        {
            onVaccineListRequestFailed()
            return
        }

        GlobalScope.launch {
            try {
                val response = repository.getVaccineList(authToken!!)

                GlobalScope.launch(Dispatchers.Main) {
                    handleVaccineListResponse(response)
                }
            } catch (e: Exception) {
                GlobalScope.launch(Dispatchers.Main) {
                    onVaccineListRequestFailed()
                }
            }
        }
    }

    private fun handleVaccineListResponse(response: Response<List<Vaccination>>) {
        if (!response.isSuccessful) {
            onVaccineListRequestFailed()
            return
        }

        onVaccineListReceived(response.body()!!)
    }


    // Parcelable implementation:

    constructor(parcel: Parcel) : this(parcel.readParcelable<Repository>(Server::class.java.classLoader)!!) {
        authToken = parcel.readString()
        passportNumber = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(repository, flags)
        parcel.writeString(authToken)
        parcel.writeString(passportNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Server> {
        override fun createFromParcel(parcel: Parcel): Server {
            return Server(parcel)
        }

        override fun newArray(size: Int): Array<Server?> {
            return arrayOfNulls(size)
        }
    }
}