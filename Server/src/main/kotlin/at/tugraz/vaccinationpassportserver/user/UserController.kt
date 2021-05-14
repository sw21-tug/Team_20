package at.tugraz.vaccinationpassportserver.user

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userRepository: UserRepository, private val bCryptPasswordEncoder: BCryptPasswordEncoder) {
    @PostMapping("/signup")
    fun signUp(@RequestBody user: User) {
        // TODO: not tested, use with caution
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()))
        userRepository.save(user)
    }

    @GetMapping("/{passportNumber}")
    fun getUserDetails(@PathVariable passportNumber: String): User? {
        return userRepository.findByPassportNumber(passportNumber)
    }
}

