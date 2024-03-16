package com.claudio.gobots.api.user.controller

import com.claudio.gobots.api.user.dto.UserDTO
import com.claudio.gobots.api.user.entity.User
import com.claudio.gobots.api.user.jwt.JwtUtills
import com.claudio.gobots.api.user.repository.UserRepository
import com.claudio.gobots.api.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager
) {

    @PostMapping
    fun createUser(@RequestBody user: UserDTO): UserDTO {

        return userService.saveUsuario(user)
    }

    @GetMapping("/login")
    fun login(@RequestBody userDTO: UserDTO): ResponseEntity<Any> {
        val jwt = loginUser(userDTO)
        return ResponseEntity.ok(jwt)
    }

    private fun loginUser(userDTO: UserDTO): String {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                userDTO.login,
                userDTO.password
            )
        )
        SecurityContextHolder.getContext().authentication = authentication
        val jwt: String = JwtUtills().generateJwtToken(authentication)
        return jwt
    }
}