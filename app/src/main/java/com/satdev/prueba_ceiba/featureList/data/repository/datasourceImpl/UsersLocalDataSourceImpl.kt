package com.satdev.prueba_ceiba.featureList.data.repository.datasourceImpl

import com.satdev.prueba_ceiba.featureList.data.db.UserDao
import com.satdev.prueba_ceiba.featureList.data.repository.datasource.UsersLocalDataSource
import com.satdev.prueba_ceiba.featureList.domain.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UsersLocalDataSourceImpl  @Inject constructor(private val userDao: UserDao)  : UsersLocalDataSource {
    override suspend fun getUserListFromDb(): List<User> {
        return userDao.getUsers()
    }

    override suspend fun insertUserToDb(list: List<User>) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.saveUsers(list)
        }
    }

    override suspend fun clearUsersDb() {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.deleteAllUsers()
        }
    }
}