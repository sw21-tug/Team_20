package at.tugraz.vaccinationpassport.backend


import at.tugraz.vaccinationpassport.backend.api.Repository
import at.tugraz.vaccinationpassport.backend.api.data.LoginDetails
import at.tugraz.vaccinationpassport.backend.api.data.ProfileData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception


class Server(private val repository: Repository) {
    private var authToken: String? = null

    var onLoginSuccessful: () -> Unit = {}
    var onLoginFailed: (Boolean) -> Unit = {}

    var onProfileReceived: (ProfileData) -> Unit = {}
    var onProfileRequestFailed: () -> Unit = {}

    fun login(loginDetails: LoginDetails) {
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
        onLoginSuccessful()
    }

    private fun handleGetProfileResponse(response: Response<ProfileData>) {
        if (!response.isSuccessful) {
            onProfileRequestFailed()
            return
        }

        onProfileReceived(response.body()!!)
    }

    fun getProfile() {

        // check if authentication token is valid
        if(authToken == null)
        {
            onProfileRequestFailed()
            return
        }
        GlobalScope.launch {
            try {
                val response = repository.getProfile()

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
}