package com.dvargas.adaschool.cornershop.dataLayer.network.dto.products

import com.dvargas.adaschool.cornershop.dataLayer.network.dto.products.pages.PageableDto
import com.dvargas.adaschool.cornershop.dataLayer.network.dto.products.pages.SortDto

data class AllProductsDto(
    val content: List<ProductDto>,
    val pageable: PageableDto,
    val totalPages: Int,
    val totalElements: Int,
    val last: Boolean,
    val size: Int,
    val number: Int,
    val sort: SortDto,
    val numberOfElements: Int,
    val first: Boolean,
    val empty: Boolean
)
