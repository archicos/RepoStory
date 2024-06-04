package com.archico.storyapp.page.maps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.archico.storyapp.data.ResultState

import com.archico.storyapp.data.response.Story
import com.archico.storyapp.repository.StoryRepository
import kotlinx.coroutines.launch

class MapsViewModel(private val storyRepository: StoryRepository): ViewModel(){
    private val _storyLocation = MutableLiveData<ResultState<List<Story>>>()
    val storyLocation: MutableLiveData<ResultState<List<Story>>> = _storyLocation
    init {
        getStoryAndLocation()
    }
    private fun getStoryAndLocation(){
        viewModelScope.launch {
            try {
                _storyLocation.value = ResultState.Loading
                val storyResponse = storyRepository.getStoriesWithLocation()
                if (storyResponse.listStory.isNotEmpty()){
                    _storyLocation.value = ResultState.Success(storyResponse.listStory)
                }else{
                    _storyLocation.value = ResultState.Error("Data not found")
                }
            }catch(e: Exception){
                _storyLocation.value = ResultState.Error(e.message.toString())
            }
        }
    }
}