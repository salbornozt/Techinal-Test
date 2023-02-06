package com.satdev.prueba_ceiba.featureDetail.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satdev.prueba_ceiba.core.data.util.Resource
import com.satdev.prueba_ceiba.featureDetail.data.model.UserPost
import com.satdev.prueba_ceiba.featureDetail.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val postRepository: PostRepository): ViewModel() {
    private val _postLiveData : MutableLiveData<Resource<List<UserPost>>> by lazy {
        MutableLiveData()
    }
    val postLiveData : LiveData<Resource<List<UserPost>>> get() = _postLiveData


    fun getUserPost(userId:Int) = viewModelScope.launch(Dispatchers.Main){
        _postLiveData.value = Resource.Loading()
        _postLiveData.value = withContext(Dispatchers.IO){
            postRepository.getUserPosts(userId)
        }
    }

}