package com.satdev.prueba_ceiba.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.satdev.prueba_ceiba.featureList.data.db.UserDao
import com.satdev.prueba_ceiba.featureList.domain.model.Address
import com.satdev.prueba_ceiba.featureList.domain.model.Company
import com.satdev.prueba_ceiba.featureList.domain.model.Geo
import com.satdev.prueba_ceiba.featureList.domain.model.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase :RoomDatabase() {
    abstract fun userDao():UserDao
}