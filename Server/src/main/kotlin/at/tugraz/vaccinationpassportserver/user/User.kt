package at.tugraz.vaccinationpassportserver.user

class User() {

    private var passportNumber: String? = null
    private var password: String? = null
    private var age: Int? = null
    private var name: String? = null
    private var nrOfVaccines: Int? = null

    constructor(passportNumber: String, password: String, age: Int, name: String, nrOfVaccines: Int) : this() {
        this.passportNumber = passportNumber
        this.password = password
        this.age = age
        this.name = name
        this.nrOfVaccines = nrOfVaccines
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

    fun getAge(): Int? {
        return this.age
    }

    fun getName(): String? {
        return this.name
    }

    fun getNrOfVaccines(): Int? {
        return this.nrOfVaccines
    }
}