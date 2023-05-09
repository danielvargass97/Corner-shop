package com.dvargas.adaschool.cornershop.domain.services.rickandmorty

import com.dvargas.adaschool.cornershop.dataLayer.dto.rickandmorty.api.characters.Result
import retrofit2.Response
import retrofit2.http.GET

interface RickAndMortyService {
    @GET("api/character")
    suspend fun getAllCharacters(): Response<Result>
}