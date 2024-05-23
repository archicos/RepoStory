package com.archico.storyapp.page.settings

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.archico.storyapp.R
import com.archico.storyapp.databinding.ActivitySettingsBinding
import com.archico.storyapp.page.ViewModelFactory
import com.archico.storyapp.page.MainViewModel
import com.archico.storyapp.page.login.LoginActivity
import com.archico.storyapp.utils.setLocale

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val mainViewModel: MainViewModel by viewModels<MainViewModel> {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setCustomView(R.layout.app_bar)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.primary)))



        binding.apply {
            mainViewModel.getThemeSetting().observe(this@SettingsActivity){
                switchDarkMode.isChecked = it
            }

            mainViewModel.getLanguageSetting().observe(this@SettingsActivity){
                switchLanguage.isChecked = it == "en"
            }

            switchLanguage.setOnCheckedChangeListener { _, isChecked ->
                setLocale(this@SettingsActivity, if (isChecked) "en" else "in")
                switchLanguage.isChecked = isChecked
                mainViewModel.setLanguageSetting(if (isChecked) "en" else "in")
            }
            switchDarkMode.setOnCheckedChangeListener{
                _, isChecked ->

                AppCompatDelegate.setDefaultNightMode(if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
                switchDarkMode.isChecked = isChecked
                mainViewModel.setThemeSetting(switchDarkMode.isChecked)
            }
            val intentLogout = Intent(this@SettingsActivity, LoginActivity::class.java)
            actionLogout.setOnClickListener {
                mainViewModel.logout()

                intentLogout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intentLogout)
                finish()
            }
        }
    }

}