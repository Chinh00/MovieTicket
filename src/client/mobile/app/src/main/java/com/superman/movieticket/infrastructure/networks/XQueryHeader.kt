package com.superman.movieticket.infrastructure.networks

import com.google.gson.Gson

data class XQueryHeader(
    var includes: MutableList<String>,
    var filters: MutableList<FilterModel>,
    var sortBy: MutableList<String>,
    var page: Int,
    var pageSize: Int,
) : BaseCustomHeader() {
    override fun JsonSerializer(): String = Gson().toJson(this)
}

data class FilterModel (val fieldName: String, val comparision: String, val fieldValue: String)
val defaultXQueryHeader: XQueryHeader = XQueryHeader(
    includes = mutableListOf(),
    filters = mutableListOf(),
    sortBy = mutableListOf(),
    page = 1,
    pageSize = 20
)