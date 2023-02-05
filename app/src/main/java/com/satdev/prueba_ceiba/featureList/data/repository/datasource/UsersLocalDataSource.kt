package com.satdev.prueba_ceiba.featureList.data.repository.datasource

import com.satdev.prueba_ceiba.featureList.domain.model.User

interface UsersLocalDataSource {
    suspend fun getUserListFromDb():List<User>

    suspend fun insertUserToDb(list: List<User>)

    suspend fun clearUsersDb()
}