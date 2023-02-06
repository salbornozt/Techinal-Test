package com.satdev.prueba_ceiba.featureList.data.model

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.satdev.prueba_ceiba.core.data.db.AppDatabase
import com.satdev.prueba_ceiba.featureList.data.db.UserDao
import kotlinx.coroutines.runBlocking

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class UserLocalDbTest {
    private lateinit var userDao: UserDao
    private lateinit var db: AppDatabase

    companion object{
        val DEFAULT_USER =User(
            name = "Prueba",
            username = "Prueba",
            email = "prueba@prueba.com",
            address = Address(street = "", suite = "", city = "", zipcode = "", geo = Geo(lat = "", lng = "")),
            phone =  "2312",
            website = "asa.com",
            company = Company(name = "", catchPhrase = "", bs = "")
        )

    }

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
        userDao = db.userDao()

    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    fun save_users_and_list_completes_successfully(){
        runBlocking {
            //Arrange
            val list = listOf<User>(DEFAULT_USER)
            //Act
            userDao.saveUsers(list)
            //Assert
            val result = userDao.getUsers()
            assertThat(result.get(0).name).isEqualTo(DEFAULT_USER.name)
        }
    }

    @Test
    fun clear_db_returns_emptyList() = runBlocking{
        //Arrange
        val list = listOf<User>(DEFAULT_USER)
        //Act
        userDao.saveUsers(list)
        userDao.deleteAllUsers()
        //Assert
        val result = userDao.getUsers()
        assertThat(result.size).isEqualTo(0)

    }





}