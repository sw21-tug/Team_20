package at.tugraz.vaccinationpassport.backend


import at.tugraz.vaccinationpassport.backend.api.Repository
import at.tugraz.vaccinationpassport.backend.api.data.LoginDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.ConnectException


class Server(private val repository: Repository) {
    private lateinit var authToken: String

    fun login(loginDetails: LoginDetails) {
        GlobalScope.launch {
            try {
                val response = repository.pushLogin(loginDetails)

                GlobalScope.launch(Dispatchers.Main) {
                    handleLoginResponse(response)
                }
            } catch (e: ConnectException) {
                GlobalScope.launch(Dispatchers.Main) {
                    return@launch
                }
            }
        }
    }

    private fun handleLoginResponse(response: Response<Unit>) {

    }
}