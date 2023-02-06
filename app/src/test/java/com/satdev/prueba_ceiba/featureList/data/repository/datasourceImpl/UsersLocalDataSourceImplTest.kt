package com.satdev.prueba_ceiba.featureList.data.repository.datasourceImpl

import com.google.common.truth.Truth.assertThat
import com.satdev.prueba_ceiba.featureList.data.db.UserDao
import com.satdev.prueba_ceiba.featureList.data.model.User
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class UsersLocalDataSourceImplTest {

    private lateinit var userDao: UserDao
    private lateinit var SUT : UsersLocalDataSourceImpl

    companion object{
        private val ID = 1
        private val LIST = listOf<User>(
            User(ID)
        )
    }

    @Before
    fun setUp() {
        userDao = mock<UserDao> {  }
        SUT = UsersLocalDataSourceImpl(userDao)
    }

    @Test(expected = Exception::class)
    fun getUserListFromDb_throwsException() {
        runBlocking {
            //Arrange
            getListExceptionError()
            //Act
            SUT.getUserListFromDb()
            verify(userDao).getUsers()
            //Assert
        }

    }

    @Test
    fun getUserListFromDb_listEmpty() {
        runBlocking {
            //Arrange
            getListEmpty()
            //Act
            val result = SUT.getUserListFromDb()
            verify(userDao).getUsers()
            val expected = listOf<User>()
            //Assert
            assertThat(result).isEqualTo(expected)
        }

    }

    @Test
    fun insertUserToDb() {
        //Arrange
        //Act
        //Assert
    }

    @Test
    fun clearUsersDb() {
        //Arrange
        //Act
        //Assert
    }

    private suspend fun getListEmpty() {
        whenever(userDao.getUsers()).thenReturn(listOf())
    }

    private suspend fun getListExceptionError() {
        doThrow(Exception()).`when`(userDao).getUsers()
    }
}