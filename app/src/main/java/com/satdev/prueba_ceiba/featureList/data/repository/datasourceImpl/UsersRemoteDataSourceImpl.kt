package com.satdev.prueba_ceiba.featureList.data.repository.datasourceImpl

import com.satdev.prueba_ceiba.core.data.api.ApiService
import com.satdev.prueba_ceiba.featureList.domain.model.User
import com.satdev.prueba_ceiba.featureList.data.repository.datasource.UsersRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class UsersRemoteDataSourceImpl  @Inject constructor(private val apiService: ApiService) : UsersRemoteDataSource{
    override suspend fun getUsers(): Response<List<User>> {
        return apiService.getAllUsers()
    }
}