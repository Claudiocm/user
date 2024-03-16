package com.claudio.gobots.api.user.repository

import com.claudio.gobots.api.user.entity.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

interface UserRepository : MongoRepository<User, String> {
    fun findByUserName(username: String): User
}