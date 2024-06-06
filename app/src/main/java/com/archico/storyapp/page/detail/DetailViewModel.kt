package com.archico.storyapp.page.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.archico.storyapp.data.ResultState
import com.archico.storyapp.data.response.Story
import com.archico.storyapp.repository.StoryRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetailViewModel(
    private val storyRepository: StoryRepository
): ViewModel(){
    private var _story = MutableLiveData<ResultState<Story>>()
    val story = _story
    fun getDetailStory(id: String){
        viewModelScope.launch {
            try {
                _story.value = ResultState.Loading
                storyRepository.getToken().collect{
                    token ->
                    if (token != null) {
                        val response = storyRepository.getDetailStory(id)
                        if (!response.error){
                            _story.value = ResultState.Success(response.story)
                        }
                    }else{
                        _story.value = ResultState.Error("Invalid token")
                    }
                }
            }catch (e:HttpException){
                _story.value = ResultState.Error(e.message())
            }
        }
    }
}