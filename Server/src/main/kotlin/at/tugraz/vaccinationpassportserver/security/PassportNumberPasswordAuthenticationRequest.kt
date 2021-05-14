package at.tugraz.vaccinationpassportserver.security

class PassportNumberPasswordAuthenticationRequest() {
    private var passportNumber: String? = null
    private var password: String? = null

    constructor(passportNumber: String, password: String) : this() {
        this.passportNumber = passportNumber
        this.password = password
    }

    fun getPassportNumber(): String? {
        return passportNumber
    }

    fun getPassword(): String? {
        return password
    }
}