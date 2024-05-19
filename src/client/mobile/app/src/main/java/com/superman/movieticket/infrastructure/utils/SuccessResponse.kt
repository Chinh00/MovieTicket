package com.superman.movieticket.infrastructure.utils

import com.superman.movieticket.core.entity.BaseEntity

interface SuccessResponse<TData> {
    public val data: TData
    public val isError: Boolean
    public val message: String
}

interface ListResponse<TData> {
    public val items: Array<TData>
    public val totalItems: Number
    public val page: Number
    public val pageSize: Number
}