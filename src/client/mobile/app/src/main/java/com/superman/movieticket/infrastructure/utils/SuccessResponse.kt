package com.superman.movieticket.infrastructure.utils

import com.superman.movieticket.core.entity.BaseEntity
import com.superman.movieticket.domain.entities.Movie

data class SuccessResponse<TData> ( val data: TData, val isError: Boolean, val message: String)

data class ListResponse<TData> (val items: List<TData>, val totalItems: Number, val page: Number, val pageSize: Number)