package com.dvargas.adaschool.cornershop.domain.services

import com.dvargas.adaschool.cornershop.dataLayer.dto.products.AllProductsDto
import com.dvargas.adaschool.cornershop.dataLayer.dto.products.CreateProductDto
import com.dvargas.adaschool.cornershop.dataLayer.dto.products.ProductDto
import retrofit2.Response
import retrofit2.http.*

interface ProductService {

    @POST("v1/products")
    suspend fun createProduct(@Body createProductDto: CreateProductDto): Response<ProductDto>

    @GET("v1/products")
    suspend fun getAllProducts(): Response<AllProductsDto>

    @PUT("v1/products/{id}")
    suspend fun updateProductById(
        @Path("id") id: String, @Body productDto: ProductDto
    ): Response<ProductDto>

    @DELETE("v1/products/{id}")
    suspend fun deleteProductById(@Path("id") id: String)
}