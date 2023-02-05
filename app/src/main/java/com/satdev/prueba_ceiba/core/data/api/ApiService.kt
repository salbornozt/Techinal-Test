package com.satdev.prueba_ceiba.core.data.api

import com.satdev.prueba_ceiba.featureList.domain.model.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getAllUsers() :Response<List<User>>
}