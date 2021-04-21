package at.tugraz.vaccinationpassportserver.security

import at.tugraz.vaccinationpassportserver.secretKey
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationFilter(authenticationManager: AuthenticationManager?) : BasicAuthenticationFilter(authenticationManager) {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val header = request.getHeader("Authorization")
        if (header == null || !header.startsWith("Bearer")) {
            filterChain.doFilter(request, response)
            return
        }
        val authenticationToken = getAuthentication(request)
        SecurityContextHolder.getContext().authentication = authenticationToken
        filterChain.doFilter(request, response)
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token = request.getHeader("Authorization")
        if (token != null) {
            val user = Jwts.parserBuilder().setSigningKey(secretKey).build()
                    .parseClaimsJws(token.replace("Bearer", ""))
                    .body
                    .subject
            return if (user != null) {
                UsernamePasswordAuthenticationToken(user, null, ArrayList())
            } else null
        }
        return null
    }
}