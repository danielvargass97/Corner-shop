package com.dvargas.adaschool.cornershop.dataLayer.network.dto.auth

data class ResponseAuthDto(
    val token: String,
    val expirationDate: String
)
