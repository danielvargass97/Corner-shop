package com.dvargas.adaschool.cornershop.dataLayer.dto.products.api.users

data class UserDto(
    val id: String,
    val name: String,
    val lastName: String,
    val email: String,
    val createAt: String,
    val roles: List<String>,
    val encryptedPassword: String
)
