package com.archico.storyapp.page.detail

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.archico.storyapp.R
import com.archico.storyapp.data.ResultState
import com.archico.storyapp.databinding.ActivityDetailStoryBinding
import com.archico.storyapp.page.ViewModelFactory

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var detailStoryBinding: ActivityDetailStoryBinding
    private val viewModelFactory = ViewModelFactory.getInstance(this)
    private val detailViewModel by viewModels<DetailViewModel> {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailStoryBinding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(detailStoryBinding.root)
        setupActionBar()

        val id = intent.getStringExtra(EXTRA_ID)
        if(id != null){
            detailViewModel.getDetailStory(id)
        }
        detailViewModel.story.observe(this){
            story ->
            when(story){
                is ResultState.Loading -> {
                    detailStoryBinding.layShimmer.startShimmer()
                }
                is ResultState.Success -> {
                    detailStoryBinding.apply {
                        layShimmer.apply {
                            stopShimmer()
                            visibility = View.GONE
                        }
                        tvStoryTitle.text = story.data.name
                        tvStoryDesc.text = story.data.description
                        Glide.with(this@DetailStoryActivity)
                            .load(story.data.photoUrl)
                            .into(imgStory)
                    }
                }
                is ResultState.Error -> {
                    Toast.makeText(this, story.error, Toast.LENGTH_SHORT).show()
                }
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
    companion object {
        const val EXTRA_ID = "extra_id"
    }
}