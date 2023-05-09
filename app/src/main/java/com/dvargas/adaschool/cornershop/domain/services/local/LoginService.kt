package com.dvargas.adaschool.cornershop.domain.services.local

import com.dvargas.adaschool.cornershop.dataLayer.dto.local.api.LoginDto
import com.dvargas.adaschool.cornershop.dataLayer.dto.local.api.LoginResponse
import com.dvargas.adaschool.cornershop.dataLayer.dto.local.api.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LoginService {

    @POST("login")
    suspend fun getLogin(@Body loginDto: LoginDto): Response<LoginResponse>

    @GET("is/register/{email}")
    suspend fun isUserRegister(@Path("email") email: String): Response<Boolean>

    @POST("register")
    suspend fun registerUser(@Body userDto: UserDto): Response<UserDto>
}