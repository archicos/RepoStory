package com.archico.storyapp.page.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.archico.storyapp.data.response.Story
import com.archico.storyapp.repository.StoryRepository

class MainViewModel(storyRepository: StoryRepository,): ViewModel(){
    val storyList: LiveData<PagingData<Story>> =
        storyRepository.getStory().cachedIn(viewModelScope)
}