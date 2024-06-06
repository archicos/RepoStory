package com.archico.storyapp.page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.archico.storyapp.utils.SettingPreferences
import kotlinx.coroutines.launch

class BaseViewModel(
    private val pref: SettingPreferences
): ViewModel(){
    fun getToken() = pref.getToken().asLiveData()
    fun logout(){
        viewModelScope.launch {
            pref.clearToken()
        }
    }
    fun getLanguageSetting() = pref.getLanguageSetting().asLiveData()

    fun setLanguageSetting(language:String){
        viewModelScope.launch {
            pref.saveLanguageSetting(language)
        }
    }
    fun getThemeSetting() = pref.getThemeSetting().asLiveData()

    fun setThemeSetting(isDarkModeActive:Boolean){
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}