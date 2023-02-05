package com.satdev.prueba_ceiba.featureList.data.repository

import android.util.Log
import com.satdev.prueba_ceiba.core.data.util.Resource
import com.satdev.prueba_ceiba.featureList.data.repository.datasource.UsersCacheDataSource
import com.satdev.prueba_ceiba.featureList.data.repository.datasource.UsersLocalDataSource
import com.satdev.prueba_ceiba.featureList.domain.model.User
import com.satdev.prueba_ceiba.featureList.data.repository.datasource.UsersRemoteDataSource
import com.satdev.prueba_ceiba.featureList.domain.repository.UsersRepository

class UsersRepositoryImpl constructor(
    private val usersRemoteDataSource: UsersRemoteDataSource,
    private val usersLocalDataSource: UsersLocalDataSource,
    private val usersCacheDataSource: UsersCacheDataSource
) : UsersRepository {
    override suspend fun getUsersList(): Resource<List<User>> {
        return getUsersFromCache()
    }

    suspend fun getUsersFromCache(): Resource<List<User>> {
        lateinit var userList: List<User>
        try {
            userList = usersCacheDataSource.getUsersFromCache()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("TAG", "getUsersFromCache: ", e)
            return Resource.Error(e.message ?: "Error al traer resultados")
        }
        if (userList.isNotEmpty()){
            return Resource.Success(userList)
        }else {
            userList = getUsersFromDb().data ?: listOf()
            usersCacheDataSource.saveUsersToCache(userList)
        }
        return Resource.Success(userList)
    }

    private suspend fun getUsersFromDb(): Resource<List<User>> {
        lateinit var userList: List<User>
        try {
            userList = usersLocalDataSource.getUserListFromDb()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("TAG", "getUsersFromDb: ",e)
            return Resource.Error(e.message ?: "Ocurrió un error al leer los reultados")
        }
        if (userList.isNotEmpty()){
            return Resource.Success(userList)
        }else{
            userList = getUsersFromWebService().data ?: listOf()
            usersLocalDataSource.insertUserToDb(userList)
        }
        return Resource.Success(userList)
    }

    private suspend fun getUsersFromWebService(): Resource<List<User>> {
        lateinit var userList: List<User>
        try {
            val response = usersRemoteDataSource.getUsers()
            val body = response.body()
            if (body != null){
                userList = body
            }
        }catch (e:Exception){
            e.printStackTrace()
            Log.e("TAG", "getUsersFromWebService: ", e)
            return Resource.Error(message = e.message ?: "Ocurrió un error al traer los resultados")
        }
        return Resource.Success(userList)
    }


}