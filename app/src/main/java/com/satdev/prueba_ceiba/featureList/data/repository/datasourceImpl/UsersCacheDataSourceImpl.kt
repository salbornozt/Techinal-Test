package com.satdev.prueba_ceiba.featureList.data.repository.datasourceImpl

import com.satdev.prueba_ceiba.featureList.data.repository.datasource.UsersCacheDataSource
import com.satdev.prueba_ceiba.featureList.domain.model.User

class UsersCacheDataSourceImpl :UsersCacheDataSource {
    private var userList = ArrayList<User>()

    override suspend fun getUsersFromCache(): List<User> {
        return userList
    }

    override suspend fun saveUsersToCache(list: List<User>) {
        userList.clear()
        userList  = ArrayList(list)
    }
}