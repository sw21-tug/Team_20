package at.tugraz.vaccinationpassportserver.user

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserDetailsServiceImpl(userRepository: UserRepository) : UserDetailsService {
    private val userRepository: UserRepository = userRepository

    override fun loadUserByUsername(username: String): UserDetails {
        val user: User = userRepository.findByPassportNumber(username)
                ?: throw UsernameNotFoundException(username)
        val authority: Collection<GrantedAuthority> = if (user.isDoctor() == true) {
            listOf(GrantedAuthority { "doctor" })
        } else {
            Collections.emptyList()
        }

        return org.springframework.security.core.userdetails.User(user.getPassportNumber(), user.getPassword(), authority)
    }
}