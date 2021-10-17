package com.example.postrequestrevisited

import com.google.gson.annotations.SerializedName

data class UserItem(
    @SerializedName("location")
    var location: String?=null,
    @SerializedName("name")
    var name: String?=null,
    @SerializedName("pk")
    var pk: Int?= null
)