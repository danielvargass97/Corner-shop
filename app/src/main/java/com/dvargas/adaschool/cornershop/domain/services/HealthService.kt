package com.dvargas.adaschool.cornershop.domain.services

import retrofit2.Response
import retrofit2.http.GET

interface HealthService {

    @GET("health")
    suspend fun health(): Response<Unit>
}