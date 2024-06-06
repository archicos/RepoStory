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
import com.archico.storyapp.page.BaseViewModel
import com.archico.storyapp.page.login.LoginActivity
import com.archico.storyapp.utils.setLocale

class SettingsActivity : AppCompatActivity() {

    private lateinit var settingsBinding: ActivitySettingsBinding
    private val viewModelFactory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val baseViewModel: BaseViewModel by viewModels<BaseViewModel> {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(settingsBinding.root)
        setupActionBar()

        settingsBinding.apply {
            baseViewModel.getLanguageSetting().observe(this@SettingsActivity){
                swtchLang.isChecked = it == "en"
            }
            baseViewModel.getThemeSetting().observe(this@SettingsActivity){
                swtchDark.isChecked = it
            }
            swtchDark.setOnCheckedChangeListener{
                _, isChecked ->
                AppCompatDelegate.setDefaultNightMode(if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
                swtchDark.isChecked = isChecked
                baseViewModel.setThemeSetting(swtchDark.isChecked)
            }
            swtchLang.setOnCheckedChangeListener { _, isChecked ->
                setLocale(this@SettingsActivity, if (isChecked) "en" else "in")
                swtchLang.isChecked = isChecked
                baseViewModel.setLanguageSetting(if (isChecked) "en" else "in")
            }

            val intentLogout = Intent(this@SettingsActivity, LoginActivity::class.java)
            btnLogout.setOnClickListener {
                baseViewModel.logout()
                intentLogout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intentLogout)
                finish()
            }
        }
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            setCustomView(R.layout.app_bar)
            setDisplayShowCustomEnabled(true)
            setDisplayShowTitleEnabled(false)
            setBackgroundDrawable(ColorDrawable(getColor(R.color.primary)))
        }
    }
}