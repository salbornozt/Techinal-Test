package com.satdev.prueba_ceiba.featureList.data.repository

import com.google.common.truth.Truth.assertThat
import com.satdev.prueba_ceiba.core.data.util.Resource
import com.satdev.prueba_ceiba.featureList.data.model.Address
import com.satdev.prueba_ceiba.featureList.data.model.Company
import com.satdev.prueba_ceiba.featureList.data.model.Geo
import com.satdev.prueba_ceiba.featureList.data.model.User
import com.satdev.prueba_ceiba.featureList.data.repository.datasource.UsersCacheDataSource
import com.satdev.prueba_ceiba.featureList.data.repository.datasource.UsersLocalDataSource
import com.satdev.prueba_ceiba.featureList.data.repository.datasource.UsersRemoteDataSource
import com.satdev.prueba_ceiba.featureList.domain.repository.UsersRepository
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import retrofit2.Response

class UsersRepositoryImplTest {

    private lateinit var usersCacheDataSource: UsersCacheDataSource
    private lateinit var usersLocalDataSource: UsersLocalDataSource
    private lateinit var usersRemoteDataSource: UsersRemoteDataSource
    private lateinit var SUT : UsersRepository

    companion object {
        val DEFAULT_USER =User(
            name = "Prueba",
            username = "Prueba",
            email = "prueba@prueba.com",
            address = Address(street = "", suite = "", city = "", zipcode = "", geo = Geo(lat = "", lng = "")),
            phone =  "2312",
            website = "asa.com",
            company = Company(name = "", catchPhrase = "", bs = "")
        )
        val LIST = listOf<User>(DEFAULT_USER)
    }

    @Before
    fun setUp() {
        usersCacheDataSource = mock<UsersCacheDataSource> {  }
        usersLocalDataSource = mock {  }
        usersRemoteDataSource = mock {  }
        SUT = UsersRepositoryImpl(usersRemoteDataSource, usersLocalDataSource, usersCacheDataSource)
    }

    @Test
    fun getUsersList_cache_return_error() = runBlocking{
        //Arrange
        getErrorFromCache()
        //Act
        val result = SUT.getUsersList()
        val expected= Resource.Error<List<User>>(RuntimeException("Error").message!!)
        //Assert
        assertThat(result).isInstanceOf(Resource.Error::class.java)
        assertThat(result.message).isEqualTo(expected.message)
    }



    @Test
    fun getUsersList_local_db_return_error() = runBlocking{
        //Arrange
        getEmptyListFromCache()
        getErrorOnLocalDb()
        //Act
        val result = SUT.getUsersList()
        val expected= Resource.Error<List<User>>(RuntimeException("Error").message!!)
        //Assert
        assertThat(result).isInstanceOf(Resource.Error::class.java)
        assertThat(result.message).isEqualTo(expected.message)
    }

    @Test
    fun getUsersList_remote_service_return_error() = runBlocking{
        //Arrange
        getEmptyListFromCache()
        getEmptyListFromLocalDb()
        getErrorFromRemoteService()
        //Act
        val result = SUT.getUsersList()
        val expected= Resource.Error<List<User>>(RuntimeException("Error").message!!)
        //Assert
        assertThat(result).isInstanceOf(Resource.Error::class.java)
        assertThat(result.message).isEqualTo(expected.message)
    }

    @Test
    fun getUsersList_call_remote_then_local_then_cache() = runBlocking{
        //Arrange
        getEmptyListFromCache()
        getEmptyListFromLocalDb()
        getSuccessFromRemoteService()
        //Act
        var result = SUT.getUsersList()
        var expected= Resource.Success<List<User>>(LIST)
        //Assert

        // --- remote ---
        verify(usersCacheDataSource, times(1)).getUsersFromCache()
        verify(usersLocalDataSource, times(1)).getUserListFromDb()
        verify(usersRemoteDataSource, times(1)).getUsers()

        assertThat(result).isInstanceOf(Resource.Success::class.java)
        assertThat(result.data).isEqualTo(expected.data)


        getEmptyListFromCache()
        getSuccessListFromLocalDb()

        // --- local
        result = SUT.getUsersList()
        expected= Resource.Success<List<User>>(LIST)

        verify(usersCacheDataSource, times(2)).getUsersFromCache()
        verify(usersLocalDataSource, times(2)).getUserListFromDb()
        verify(usersRemoteDataSource, times(1)).getUsers()

        assertThat(result).isInstanceOf(Resource.Success::class.java)
        assertThat(result.data).isEqualTo(expected.data)



        //--- cache
        getSuccessListFromCache()


        result = SUT.getUsersList()
        expected= Resource.Success<List<User>>(LIST)

        verify(usersCacheDataSource, times(3)).getUsersFromCache()
        verify(usersLocalDataSource, times(2)).getUserListFromDb()
        verify(usersRemoteDataSource, times(1)).getUsers()

        assertThat(result).isInstanceOf(Resource.Success::class.java)
        assertThat(result.data).isEqualTo(expected.data)



    }

    private suspend fun getSuccessListFromCache() {
        whenever(usersCacheDataSource.getUsersFromCache()).thenReturn(LIST)
    }

    private suspend fun getSuccessListFromLocalDb() {
        whenever(usersLocalDataSource.getUserListFromDb()).thenReturn(LIST)

    }

    private suspend fun getSuccessFromRemoteService() {
        whenever(usersRemoteDataSource.getUsers()).thenReturn(Response.success(LIST))
    }


    private suspend fun getErrorFromRemoteService() {
        doThrow(RuntimeException("Error")).`when`(usersRemoteDataSource).getUsers()
    }

    private suspend fun getEmptyListFromLocalDb() {
        whenever(usersLocalDataSource.getUserListFromDb()).thenReturn(listOf())
    }

    private suspend fun getEmptyListFromCache() {
        whenever(usersCacheDataSource.getUsersFromCache()).thenReturn(listOf())
    }

    private suspend fun getErrorOnLocalDb() {
        doThrow(RuntimeException("Error")).`when`(usersLocalDataSource).getUserListFromDb()

    }
    private suspend fun getErrorFromCache() {
        doThrow(RuntimeException("Error")).`when`(usersCacheDataSource).getUsersFromCache()
    }
}