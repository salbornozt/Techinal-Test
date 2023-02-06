package com.satdev.prueba_ceiba.core.data.api

import com.satdev.prueba_ceiba.featureDetail.data.model.UserPost
import com.satdev.prueba_ceiba.featureList.data.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getAllUsers() :Response<List<User>>

    @GET("posts")
    suspend fun getUserPosts(@Query("userId") userId:Int) :Response<List<UserPost>>
}