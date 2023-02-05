package com.satdev.prueba_ceiba.featureList.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satdev.prueba_ceiba.core.data.util.Resource
import com.satdev.prueba_ceiba.featureList.domain.model.User
import com.satdev.prueba_ceiba.featureList.domain.repository.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val usersRepository: UsersRepository): ViewModel() {
    private val _userListLiveData : MutableLiveData<Resource<List<User>>> by lazy {
        MutableLiveData<Resource<List<User>>>()
    }



    val userListLiveData : LiveData<Resource<List<User>>> get() = _userListLiveData

    init {
        getUserList()
    }

    fun getUserList() = viewModelScope.launch(Dispatchers.Main) {
        _userListLiveData.value = Resource.Loading()

        _userListLiveData.value =  withContext(kotlinx.coroutines.Dispatchers.IO) {
            usersRepository.getUsersList()
        }
    }

}