package com.satdev.prueba_ceiba.featureDetail.data.repository.dataSource

import com.satdev.prueba_ceiba.featureDetail.data.model.UserPost
import retrofit2.Response

interface PostRemoteDataSource {

    suspend fun getUserPost(userId:Int):Response<List<UserPost>>
}