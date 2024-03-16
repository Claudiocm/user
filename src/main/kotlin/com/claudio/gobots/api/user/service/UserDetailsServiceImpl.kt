package com.claudio.gobots.api.user.service

import com.claudio.gobots.api.user.dto.UserDTO
import com.claudio.gobots.api.user.entity.User
import com.claudio.gobots.api.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user: User = userRepository.findByUserName(username)

        if (user.equals("")) {
            throw UsernameNotFoundException("No user with this user name")
        } else {
            //System.out.println(user.name)
        }

        return org.springframework.security.core.userdetails.User.builder()
            .username(user.login)
            .password(user.password)
            .build()
    }

}