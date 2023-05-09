package com.dvargas.adaschool.cornershop.domain.services.products

import com.dvargas.adaschool.cornershop.dataLayer.dto.products.api.auth.AuthDto
import com.dvargas.adaschool.cornershop.dataLayer.dto.products.api.auth.ResponseAuthDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("v1/auth")
    suspend fun getAuth(@Body authDto: AuthDto): Response<ResponseAuthDto>
}