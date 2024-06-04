package com.archico.storyapp.page

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.archico.storyapp.R
import com.archico.storyapp.page.home.MainActivity
import com.archico.storyapp.page.login.LoginActivity
import com.archico.storyapp.utils.setLocale

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        val viewModelFactory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val baseViewModel: BaseViewModel by viewModels<BaseViewModel> {
            viewModelFactory
        }

        Handler(Looper.getMainLooper()).postDelayed(
            {
                baseViewModel.getThemeSetting().observe(this){
                        isDarkModeActive ->
                    AppCompatDelegate.setDefaultNightMode(if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
                }

                baseViewModel.getLanguageSetting().observe(this){
                    language ->
                    setLocale(this,language)
                }
                baseViewModel.getToken().observe(this){
                    token ->
                    val intentActivity = Intent(this, if (token == null) LoginActivity::class.java else MainActivity::class.java)
                    startActivity(intentActivity)
                    finish()
                }
            },
            SPLASH_TIME_OUT
        )
    }

    companion object {
        const val SPLASH_TIME_OUT = 3000L
    }
}