package at.tugraz.vaccinationpassportserver.user

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class UserRepository(private val bCryptPasswordEncoder: BCryptPasswordEncoder) {
    private var users: MutableList<User> = mutableListOf()

    //TODO: bCryptPasswordEncoder and initializer only for current testing purposes
    init {
        users.add(User("admin", bCryptPasswordEncoder.encode("password"), 20, "Admin", 0))
        users.add(User("12345678", bCryptPasswordEncoder.encode("password"), 30, "Max Mustermann", 4))
        users.add(User("11223344", bCryptPasswordEncoder.encode("password"), 65, "Dr. Goodheart", 0))
        users[1].addVaccine(Vaccine("Covid", "21-01-2021"))
        users[1].addVaccine(Vaccine("FSME", "29-05-2015"))
    }

    fun findByPassportNumber(passportNumber: String): User? {
        return try {
            users.first({ user: User -> user.getPassportNumber() == passportNumber })
        } catch (e: NoSuchElementException) {
            null
        }
    }

    fun save(user: User) {
        users.add(user)
    }
}