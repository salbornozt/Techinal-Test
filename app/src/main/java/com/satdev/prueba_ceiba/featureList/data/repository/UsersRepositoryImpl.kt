package com.satdev.prueba_ceiba.featureList.data.repository

import com.satdev.prueba_ceiba.core.data.util.Resource
import com.satdev.prueba_ceiba.featureList.data.repository.datasource.UsersCacheDataSource
import com.satdev.prueba_ceiba.featureList.data.repository.datasource.UsersLocalDataSource
import com.satdev.prueba_ceiba.featureList.data.model.User
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

    private suspend fun getUsersFromCache(): Resource<List<User>> {
        lateinit var userList: List<User>
        try {
            userList = usersCacheDataSource.getUsersFromCache()
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(e.message ?: "Error al traer resultados")
        }
        if (userList.isNotEmpty()){
            return Resource.Success(userList)
        }else {
            when(val result = getUsersFromDb()){
                is Resource.Success->{
                    userList = result.data ?: listOf()
                    usersCacheDataSource.saveUsersToCache(userList)
                }
                else ->{
                    return result
                }
            }


        }
        return Resource.Success(userList)
    }

    private suspend fun getUsersFromDb(): Resource<List<User>> {
        lateinit var userList: List<User>
        try {
            userList = usersLocalDataSource.getUserListFromDb()
        } catch (e: Exception) {
            e.printStackTrace()
            return Resource.Error(e.message ?: "Ocurrió un error al leer los reultados")
        }
        if (userList.isNotEmpty()){
            return Resource.Success(userList)
        }else{
            when(val result = getUsersFromWebService()){
                is Resource.Success->{
                    userList = result.data ?: listOf()
                    usersLocalDataSource.insertUserToDb(userList)
                }
                else ->{
                    return result
                }
            }

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
            return Resource.Error(message = e.message ?: "Ocurrió un error al traer los resultados")
        }
        return Resource.Success(userList)
    }


}