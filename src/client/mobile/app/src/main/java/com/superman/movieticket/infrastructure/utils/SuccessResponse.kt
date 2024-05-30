package com.superman.movieticket.infrastructure.utils

import com.superman.movieticket.core.entity.BaseEntity

data class SuccessResponse<TData> (
    val data: TData?,
    val isError: Boolean,
    val message: String
)

data class ListResponse<TData> (
    public val items: List<TData>,
    public val totalItems: Number,
    public val page: Number,
    public val pageSize: Number,
)