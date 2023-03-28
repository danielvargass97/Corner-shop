package com.dvargas.adaschool.cornershop.dataLayer.network.dto.products.pages

data class PageableDto(
    val sort: SortDto,
    val offset: Int,
    val pageNumber: Int,
    val pageSize: Int,
    val paged: Boolean,
    val unpaged: Boolean
)
