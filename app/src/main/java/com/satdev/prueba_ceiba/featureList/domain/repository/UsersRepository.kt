package com.satdev.prueba_ceiba.featureList.domain.repository

import com.satdev.prueba_ceiba.core.data.util.Resource
import com.satdev.prueba_ceiba.featureList.domain.model.User

interface UsersRepository {
    suspend fun getUsersList(): Resource<List<User>>
}