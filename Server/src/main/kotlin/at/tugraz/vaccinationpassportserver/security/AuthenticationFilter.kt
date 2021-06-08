package at.tugraz.vaccinationpassportserver.security

import at.tugraz.vaccinationpassportserver.secretKey
import at.tugraz.vaccinationpassportserver.user.User
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(authenticationManager: AuthenticationManager?) : UsernamePasswordAuthenticationFilter(authenticationManager) {
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication? {
        return try {
            val creds: PassportNumberPasswordAuthenticationRequest = ObjectMapper().readValue(request.inputStream, PassportNumberPasswordAuthenticationRequest::class.java)
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(creds.getPassportNumber(), creds.getPassword(), ArrayList()))
        } catch (e: IOException) {
            throw RuntimeException("Could not read request$e")
        }
    }

    override fun successfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse, filterChain: FilterChain?, authentication: Authentication) {
        val token: String = Jwts.builder()
                .setSubject((authentication.principal as org.springframework.security.core.userdetails.User).username)
                .setExpiration(Date(System.currentTimeMillis() + 864000000))
                .signWith(secretKey)
                .compact()
        response.addHeader("Authorization", "Bearer $token")
        val user = authentication.principal as org.springframework.security.core.userdetails.User
        val doctor = user.authorities.contains(GrantedAuthority { "doctor" })
        response.addHeader("isDoctor", doctor.toString())
    }

    init {
        setFilterProcessesUrl("/login")
    }
}