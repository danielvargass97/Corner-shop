package com.dvargas.adaschool.cornershop.domain.services.digimon

import com.dvargas.adaschool.cornershop.dataLayer.dto.digimon.api.Digimon
import retrofit2.Response
import retrofit2.http.GET

interface DigimonService {
    @GET("api/digimon")
    suspend fun getAllDigimon(): Response<List<Digimon>>
}