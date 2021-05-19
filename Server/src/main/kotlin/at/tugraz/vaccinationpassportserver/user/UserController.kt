package at.tugraz.vaccinationpassportserver.user

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpClientErrorException
import java.security.Principal

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
    fun getUserDetails(principal: Principal, @PathVariable passportNumber: String): User? {
        if(principal.name != passportNumber)
            throw org.springframework.security.access.AccessDeniedException("403 returned")

        return userRepository.findByPassportNumber(passportNumber)
    }

    @GetMapping("/{passportNumber}/vaccines")
    fun getUserVaccines(principal: Principal, @PathVariable passportNumber: String): MutableList<Vaccine>? {
        if(principal.name != passportNumber)
            throw org.springframework.security.access.AccessDeniedException("403 returned")

        var user = userRepository.findByPassportNumber(passportNumber)
        return user?.getVaccines()
    }
}

