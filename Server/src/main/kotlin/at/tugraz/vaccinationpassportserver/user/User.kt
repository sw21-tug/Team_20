package at.tugraz.vaccinationpassportserver.user

import com.fasterxml.jackson.annotation.JsonIgnore

class User() {

    private var passportNumber: String? = null

    @JsonIgnore
    private var password: String? = null
    private var age: Int? = null
    private var name: String? = null
    private var nrOfVaccines: Int? = null

    @JsonIgnore
    private var isDoctor: Boolean? = null

    @JsonIgnore
    private var vaccines: MutableList<Vaccine>? = null

    constructor(passportNumber: String, password: String, age: Int, name: String, nrOfVaccines: Int, isDoctor: Boolean) : this() {
        this.passportNumber = passportNumber
        this.password = password
        this.age = age
        this.name = name
        this.nrOfVaccines = nrOfVaccines
        this.vaccines = mutableListOf<Vaccine>()
        this.isDoctor = isDoctor
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

    fun getVaccines() : MutableList<Vaccine>?
    {
        return vaccines
    }
    fun isDoctor() : Boolean?
    {
        return isDoctor
    }

    fun addVaccine(vaccine : Vaccine)
    {
        this.vaccines?.add(vaccine)
    }
}