package com.archico.storyapp.page


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.archico.storyapp.data.di.Injection
import com.archico.storyapp.repository.StoryRepository
import com.archico.storyapp.page.detail.DetailViewModel
import com.archico.storyapp.page.add.AddStoryViewModel
import com.archico.storyapp.page.home.MainViewModel
import com.archico.storyapp.page.login.LoginViewModel
import com.archico.storyapp.page.maps.MapsViewModel
import com.archico.storyapp.page.register.RegisterViewModel
import com.archico.storyapp.utils.SettingPreferences
import com.archico.storyapp.utils.dataStore

class ViewModelFactory private constructor(private val storyRepository: StoryRepository, private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BaseViewModel::class.java)) {
            return BaseViewModel(pref) as T
        }else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(storyRepository) as T
        }else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(storyRepository) as T
        }else if (modelClass.isAssignableFrom(AddStoryViewModel::class.java)) {
            return AddStoryViewModel(storyRepository) as T
        }else if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(storyRepository) as T
        }else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(storyRepository) as T
        }else if(modelClass.isAssignableFrom(MapsViewModel::class.java)){
            return MapsViewModel(storyRepository) as T
        }
        throw IllegalArgumentException("Invalid ViewModel: ${modelClass.name}")
    }
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context), pref = SettingPreferences.getInstance(context.dataStore))
            }.also { instance = it }
    }
}