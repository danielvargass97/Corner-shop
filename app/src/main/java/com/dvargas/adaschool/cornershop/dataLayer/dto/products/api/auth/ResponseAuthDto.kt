package com.dvargas.adaschool.cornershop.dataLayer.dto.products.api.auth

data class ResponseAuthDto(
    val token: String,
    val expirationDate: String
)
