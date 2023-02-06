package com.satdev.prueba_ceiba.featureDetail.data.repository.dataSourceImpl

import com.satdev.prueba_ceiba.core.data.api.ApiService
import com.satdev.prueba_ceiba.featureDetail.data.model.UserPost
import com.satdev.prueba_ceiba.featureDetail.data.repository.dataSource.PostRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class PostRemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :PostRemoteDataSource{
    override suspend fun getUserPost(userId: Int): Response<List<UserPost>> {
        return apiService.getUserPosts(userId)
    }
}