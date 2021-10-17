package com.example.postrequestrevisited
import retrofit2.Call
import retrofit2.http.*

interface APIInterface {
    @Headers("Content-Type: application/json")
    @GET("/test/")
    fun getUsersInfo(): Call<Users?>?


    @Headers("Content-Type: application/json")
    @POST("/test/")
    fun addUsersInfo(@Body requestBody: UserItem?): Call<UserItem>

    @Headers("Content-Type: application/json")
    @PUT("/test/{id}")
    fun updateUsersInfo(@Path("id")id:Int, @Body requestBody: UserItem?): Call<UserItem>

    @Headers("Content-Type: application/json")
    @DELETE("/test/{id}")
    fun deleteUsersInfo(@Path("id")id:Int): Call<Void>

}
