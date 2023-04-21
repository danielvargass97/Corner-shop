package com.dvargas.adaschool.cornershop.dataLayer.dto.auth

data class ResponseAuthDto(
    val token: String,
    val expirationDate: String
)
