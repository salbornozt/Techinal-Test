package com.satdev.prueba_ceiba.core.di

import android.content.Context
import androidx.room.Room
import com.satdev.prueba_ceiba.core.data.api.ApiService
import com.satdev.prueba_ceiba.core.data.db.AppDatabase
import com.satdev.prueba_ceiba.featureList.data.db.UserDao
import com.satdev.prueba_ceiba.featureList.data.repository.datasource.UsersRemoteDataSource
import com.satdev.prueba_ceiba.featureList.data.repository.datasourceImpl.UsersRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesRetrofit():Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit):ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext context:Context):AppDatabase{
        return Room.databaseBuilder(context,AppDatabase::class.java,"appDatabase").build()
    }

    @Singleton
    @Provides
    fun providesUserDao(appDatabase: AppDatabase):UserDao{
        return appDatabase.userDao()
    }


}