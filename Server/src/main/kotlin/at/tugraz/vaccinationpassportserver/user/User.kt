package at.tugraz.vaccinationpassportserver.user

class User() {

    private var username: String? = null
    private var password: String? = null

    constructor(username: String, password: String) : this() {
        this.username = username
        this.password = password
    }

    fun getUsername(): String? {
        return username
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String?) {
        this.password = password
    }
}