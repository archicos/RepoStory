package com.archico.storyapp.page

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.archico.storyapp.R
import com.archico.storyapp.page.maps.MapsActivity
import com.archico.storyapp.page.settings.SettingsActivity

open class BaseClass:AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.maps -> {
                val settingIntent = Intent(this, MapsActivity::class.java)
                startActivity(settingIntent)
            }
            R.id.settings -> {
                val settingIntent = Intent(this, SettingsActivity::class.java)
                startActivity(settingIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    fun setActionBar(){
        supportActionBar?.setCustomView(R.layout.app_bar)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.primary)))
    }
}