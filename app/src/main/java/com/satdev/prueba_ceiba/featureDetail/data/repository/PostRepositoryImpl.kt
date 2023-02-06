package com.satdev.prueba_ceiba.featureDetail.data.repository

import com.satdev.prueba_ceiba.core.data.util.Resource
import com.satdev.prueba_ceiba.featureDetail.data.model.UserPost
import com.satdev.prueba_ceiba.featureDetail.data.repository.dataSource.PostRemoteDataSource
import com.satdev.prueba_ceiba.featureDetail.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val postRemoteDataSource: PostRemoteDataSource) :PostRepository{
    override suspend fun getUserPosts(userId: Int): Resource<List<UserPost>> {
        return getPostFromWeb(userId)
    }
    private suspend fun getPostFromWeb(userId: Int):Resource<List<UserPost>>{
        lateinit var postList : List<UserPost>
        try {
            val result = postRemoteDataSource.getUserPost(userId)
            if (result.body() != null){
                postList = result.body()!!
            }
        }catch (e:Exception){
            e.printStackTrace()
            return Resource.Error(e.message ?: "Error al traer los post")
        }
        return Resource.Success(postList)
    }
}