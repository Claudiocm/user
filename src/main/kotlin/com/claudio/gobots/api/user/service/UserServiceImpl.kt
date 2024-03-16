package com.claudio.gobots.api.user.service

import com.claudio.gobots.api.user.dto.UserDTO
import com.claudio.gobots.api.user.entity.User
import com.claudio.gobots.api.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {
    override fun saveUsuario(user: UserDTO): UserDTO {

        val newUser = User(name = user.name, login = user.login, password = user.password)
        return userRepository.save(newUser).let { UserDTO(it.name, it.login) }

    }
}