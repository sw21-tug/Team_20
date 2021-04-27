package at.tugraz.vaccinationpassport.backend


import at.tugraz.vaccinationpassport.backend.api.Repository
import at.tugraz.vaccinationpassport.backend.api.data.LoginDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception


class Server(private val repository: Repository) {
    private lateinit var authToken: String

    var onLoginSuccessful: () -> Unit = {}
    var onLoginFailed: (Boolean) -> Unit = {}


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
}