package com.satdev.prueba_ceiba.featureDetail.domain.repository

import com.satdev.prueba_ceiba.core.data.util.Resource
import com.satdev.prueba_ceiba.featureDetail.data.model.UserPost

interface PostRepository {

    suspend fun getUserPosts(userId:Int):Resource<List<UserPost>>
}