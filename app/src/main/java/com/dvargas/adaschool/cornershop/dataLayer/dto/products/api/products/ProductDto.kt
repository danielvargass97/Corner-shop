package com.dvargas.adaschool.cornershop.dataLayer.dto.products.api.products

data class ProductDto(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val tags: String,
    val price: Double,
    val imageUrl: String
)
