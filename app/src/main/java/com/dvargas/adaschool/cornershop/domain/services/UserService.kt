package com.dvargas.adaschool.cornershop.domain.services

import com.dvargas.adaschool.cornershop.dataLayer.dto.users.AllUsersDto
import com.dvargas.adaschool.cornershop.dataLayer.dto.users.CreateUserDto
import com.dvargas.adaschool.cornershop.dataLayer.dto.users.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {

    @POST("v1/users")
    suspend fun createUser(@Body createUserDto: CreateUserDto): Response<UserDto>

    @GET("v1/users")
    suspend fun getAllUsers(): Response<AllUsersDto>

    @GET("v1/users/{id}")
    suspend fun getUserById(@Path("id") id: String): Response<UserDto>
}