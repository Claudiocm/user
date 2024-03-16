package com.claudio.gobots.api.user.jwt

import com.claudio.gobots.api.user.service.UserDetailsServiceImpl
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException


class AuthTokenFilter : OncePerRequestFilter() {
    @Autowired
    private val jwtUtills: JwtUtills? = null

    @Autowired
    private val userDetailsService: UserDetailsServiceImpl? = null

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val jwt = paresJwt(request)

            if (jwt != null && jwtUtills!!.validateJwtToken(jwt)) {
                val username = jwtUtills.getUsernameFromJwtToken(jwt)
                val userDetails = userDetailsService!!.loadUserByUsername(username)
                val authenticationToken =
                    UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authenticationToken
            }
        } catch (e: Exception) {
            Companion.logger.error("Cannot set User Authenticaion :{}", e)
        }
        filterChain.doFilter(request, response)
    }

    private fun paresJwt(request: HttpServletRequest): String? {
        val headerAuth = request.getHeader("Authorization")
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7)
        }
        return null
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AuthTokenFilter::class.java)
    }
}