package com.dvargas.adaschool.cornershop.dataLayer.network.dto.products

data class CreateProductDto(
    val name: String,
    val description: String,
    val category: String,
    val tags: String,
    val price: Double,
    val imageUrl: String
)
