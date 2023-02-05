package com.satdev.prueba_ceiba.core.di

import com.satdev.prueba_ceiba.featureList.data.db.UserDao
import com.satdev.prueba_ceiba.featureList.data.repository.datasource.UsersLocalDataSource
import com.satdev.prueba_ceiba.featureList.data.repository.datasourceImpl.UsersLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun providesLocalDataSource(userDao: UserDao):UsersLocalDataSource{
        return UsersLocalDataSourceImpl(userDao)
    }
}