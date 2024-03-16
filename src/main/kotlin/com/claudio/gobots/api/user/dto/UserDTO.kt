package com.claudio.gobots.api.user.dto

import com.fasterxml.jackson.annotation.JsonIgnore

data class UserDTO(
    val name: String,
    val login: String,
    val password: String
) {
    constructor(name: String, login: String) : this(name, login, null.toString())
}