package com.satdev.prueba_ceiba.featureList.data.repository.datasource

import com.satdev.prueba_ceiba.featureList.data.model.User
import retrofit2.Response

interface UsersRemoteDataSource {
    suspend fun getUsers():Response<List<User>>

}