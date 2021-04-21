package at.tugraz.vaccinationpassportserver.user

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class UserRepository(private val bCryptPasswordEncoder: BCryptPasswordEncoder) {
    private var users: MutableList<User> = mutableListOf()

    //TODO: bCryptPasswordEncoder and initializer only for current testing purposes
    init {
        users.add(User("admin", bCryptPasswordEncoder.encode("password")))
    }

    fun findByUsername(username: String): User? {
        return try {
            users.first({ user: User -> user.getUsername() == username })
        } catch (e: NoSuchElementException) {
            null
        }
    }

    fun save(user: User) {
        users.add(user)
    }
}