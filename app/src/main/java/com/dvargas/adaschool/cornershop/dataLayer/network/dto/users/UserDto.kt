package com.dvargas.adaschool.cornershop.dataLayer.network.dto.users

data class UserDto(
    val id: String,
    val name: String,
    val lastName: String,
    val email: String,
    val createAt: String,
    val roles: List<String>,
    val encryptedPassword: String
)
