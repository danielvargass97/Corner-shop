package com.dvargas.adaschool.cornershop.dataLayer.dto.users

data class CreateUserDto(
    val name: String,
    val lastName: String,
    val email: String,
    val password: String
)
