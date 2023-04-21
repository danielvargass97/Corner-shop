package com.dvargas.adaschool.cornershop.domain.services

import com.dvargas.adaschool.cornershop.dataLayer.dto.auth.AuthDto
import com.dvargas.adaschool.cornershop.dataLayer.dto.auth.ResponseAuthDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("v1/auth")
    suspend fun getAuth(@Body authDto: AuthDto): Response<ResponseAuthDto>
}