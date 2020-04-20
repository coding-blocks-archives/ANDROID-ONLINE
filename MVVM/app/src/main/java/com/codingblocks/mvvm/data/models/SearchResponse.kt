package com.codingblocks.mvvm.data.models

data class SearchResponse(
    val totalCount: Int? = null,
    val incompleteResults: Boolean? = null,
    val items: List<User>? = null
)
