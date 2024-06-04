package com.archico.storyapp.data.di

import android.content.Context
import com.archico.storyapp.data.retrofit.ApiConfig
import com.archico.storyapp.database.StoryDatabase
import com.archico.storyapp.repository.StoryRepository
import com.archico.storyapp.utils.SettingPreferences
import com.archico.storyapp.utils.dataStore

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val pref = SettingPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(pref)
        val database = StoryDatabase.getDatabase(context)
        return StoryRepository.getInstance(apiService, pref,database)
    }
}