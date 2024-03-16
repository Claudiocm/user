package com.claudio.gobots.api.user.service

import com.claudio.gobots.api.user.dto.UserDTO
import com.claudio.gobots.api.user.entity.User

interface UserService {
    fun saveUsuario(user: UserDTO): UserDTO
}