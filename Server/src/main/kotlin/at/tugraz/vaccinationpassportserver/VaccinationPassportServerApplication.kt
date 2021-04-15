package at.tugraz.vaccinationpassportserver

import at.tugraz.vaccinationpassportserver.user.UserRepository
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.crypto.SecretKey


@SpringBootApplication
class VaccinationPassportServerApplication

// TODO: find a better way to store the key
val secretKey: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512)

fun main(args: Array<String>) {
    runApplication<VaccinationPassportServerApplication>(*args)
}

@Configuration
class AppConfig {
    @Bean
    fun userRepository(): UserRepository {
        return UserRepository(bCryptPasswordEncoder())
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}




