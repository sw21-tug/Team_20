package at.tugraz.vaccinationpassportserver.user

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(private val userRepository: UserRepository, private val bCryptPasswordEncoder: BCryptPasswordEncoder) {
    @PostMapping("/signup")
    fun signUp(@RequestBody user: User) {
        // TODO: not tested, use with caution
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()))
        userRepository.save(user)
    }
}