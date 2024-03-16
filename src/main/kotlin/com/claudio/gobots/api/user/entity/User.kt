package com.claudio.gobots.api.user.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class User(
    @Id val id: String? = null,
    val name: String,
    val login: String,
    val password: String
)