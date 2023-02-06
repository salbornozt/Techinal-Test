package com.satdev.prueba_ceiba.core.di

import com.satdev.prueba_ceiba.core.data.api.ApiService
import com.satdev.prueba_ceiba.featureList.data.db.UserDao
import com.satdev.prueba_ceiba.featureList.data.repository.UsersRepositoryImpl
import com.satdev.prueba_ceiba.featureList.data.repository.datasource.UsersCacheDataSource
import com.satdev.prueba_ceiba.featureList.data.repository.datasource.UsersLocalDataSource
import com.satdev.prueba_ceiba.featureList.data.repository.datasource.UsersRemoteDataSource
import com.satdev.prueba_ceiba.featureList.data.repository.datasourceImpl.UsersCacheDataSourceImpl
import com.satdev.prueba_ceiba.featureList.data.repository.datasourceImpl.UsersLocalDataSourceImpl
import com.satdev.prueba_ceiba.featureList.data.repository.datasourceImpl.UsersRemoteDataSourceImpl
import com.satdev.prueba_ceiba.featureList.domain.repository.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Singleton
    @Provides
    fun providesUserRepository(
        usersRemoteDataSource: UsersRemoteDataSource,
        usersLocalDataSource: UsersLocalDataSource,
        usersCacheDataSource: UsersCacheDataSource
    ): UsersRepository {
        return UsersRepositoryImpl(
            usersRemoteDataSource,
            usersLocalDataSource,
            usersCacheDataSource
        )
    }

    @Singleton
    @Provides
    fun providesUserRemoteDataSource(apiService: ApiService):UsersRemoteDataSource{
        return UsersRemoteDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun providesLocalDataSource(userDao: UserDao):UsersLocalDataSource{
        return UsersLocalDataSourceImpl(userDao)
    }

    @Singleton
    @Provides
    fun providesUsersCacheDataSource():UsersCacheDataSource{
        return UsersCacheDataSourceImpl()
    }
}