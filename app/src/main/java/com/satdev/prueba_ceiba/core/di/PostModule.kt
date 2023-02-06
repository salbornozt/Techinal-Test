package com.satdev.prueba_ceiba.core.di

import com.satdev.prueba_ceiba.core.data.api.ApiService
import com.satdev.prueba_ceiba.featureDetail.data.repository.PostRepositoryImpl
import com.satdev.prueba_ceiba.featureDetail.data.repository.dataSource.PostRemoteDataSource
import com.satdev.prueba_ceiba.featureDetail.data.repository.dataSourceImpl.PostRemoteDataSourceImpl
import com.satdev.prueba_ceiba.featureDetail.domain.repository.PostRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostModule {

    @Singleton
    @Provides
    fun providesPostRemoteDataSource(apiService: ApiService): PostRemoteDataSource{
        return PostRemoteDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun providesPostRepository(postRemoteDataSource: PostRemoteDataSource):PostRepository{
        return PostRepositoryImpl(postRemoteDataSource)
    }
}