package com.tbadhit.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseSearch(
    @field:SerializedName("items")
    val listUsers: List<ResponseUser>
)
