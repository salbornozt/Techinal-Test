package com.satdev.prueba_ceiba.featureList.data.repository.datasource

import com.satdev.prueba_ceiba.featureList.data.model.User

interface UsersCacheDataSource {
    suspend fun getUsersFromCache():List<User>
    suspend fun saveUsersToCache(list: List<User>)

}