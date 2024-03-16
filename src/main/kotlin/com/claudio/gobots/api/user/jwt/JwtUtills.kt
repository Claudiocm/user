package com.claudio.gobots.api.user.jwt

import io.jsonwebtoken.*
import io.jsonwebtoken.impl.TextCodec.BASE64
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*


@Component
class JwtUtills {
    @Value("\${jwt.secret}")
    private val jwtSecret: String? = null

    @Value("\${jwtExpiration}")
    private val jwtExpiration = 0

    fun generateJwtToken(authentication: Authentication): String {
        val userPricipal = authentication.getPrincipal() as UserDetails
        return Jwts.builder()
            .setSubject(userPricipal.username)
            .setIssuedAt(Date())
            .setExpiration(Date(Date(System.currentTimeMillis()).time+jwtExpiration))
            .signWith(SignatureAlgorithm.HS256, key)
            .compact()
    }

    val key: ByteArray? = BASE64.decode(jwtSecret)

    fun validateJwtToken(authtoken: String?): Boolean {
        try {
            Jwts.parser().setSigningKey(key)
                .parseClaimsJws(authtoken).getBody();
            return true
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT Token : {}", e.message)
        } catch (e: ExpiredJwtException) {
            logger.error("JWT Token is Expired : {}", e.message)
        } catch (e: UnsupportedJwtException) {
            logger.error("Unsupported JWT :{}", e.message)
        } catch (e: IllegalArgumentException) {
            logger.error("JWT Payload is Empty: {}", e.message)
        }
        return false
    }

    fun getUsernameFromJwtToken(authtoken: String?): String {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(authtoken).getBody().getSubject()
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(JwtUtills::class.java)
    }
}