package at.tugraz.vaccinationpassportserver.user

class User() {

    private var passportNumber: String? = null
    private var password: String? = null

    constructor(username: String, password: String) : this() {
        this.passportNumber = username
        this.password = password
    }

    fun getPassportNumber(): String? {
        return passportNumber
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String?) {
        this.password = password
    }
}