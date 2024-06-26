package com.archico.storyapp.page.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.archico.storyapp.data.ResultState
import com.archico.storyapp.data.response.MessageResponse
import com.archico.storyapp.repository.StoryRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel(
    private val storyRepository: StoryRepository
):ViewModel() {
    private val _responseResult = MutableLiveData<ResultState<MessageResponse>>()
    val responseResult = _responseResult

    fun postRegister(name:String, email:String, password:String){
        viewModelScope.launch {
            try {
                _responseResult.value = ResultState.Loading
                val response = storyRepository.register(name,email,password)
                if (!response.error){
                    _responseResult.value = ResultState.Success(response)
                }
            }catch (e:HttpException){
                val errorBody = e.response()?.errorBody()?.string()
                _responseResult.value = ResultState.Error(errorBody?:e.message())
            }
        }
    }
}