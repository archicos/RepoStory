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

    private lateinit var binding: ActivityDetailStoryBinding
    private val factory = ViewModelFactory.getInstance(this)
    private val detailViewModel by viewModels<DetailViewModel> {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setCustomView(R.layout.app_bar)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.primary)))


        val id = intent.getStringExtra(EXTRA_ID)

        if(id != null){
            detailViewModel.getDetailStory(id)
        }

        detailViewModel.story.observe(this){
            story ->
            when(story){
                is ResultState.Loading -> {
                    binding.shimmerLayout.startShimmer()
                }
                is ResultState.Success -> {
                    binding.apply {
                        shimmerLayout.apply {
                            stopShimmer()
                            visibility = View.GONE
                        }

                        textViewTitle.text = story.data.name
                        textViewDescription.text = story.data.description

                        Glide.with(this@DetailStoryActivity)
                            .load(story.data.photoUrl)
                            .into(imageViewStory)
                    }
                }
                is ResultState.Error -> {
                    Toast.makeText(this, story.error, Toast.LENGTH_SHORT).show()
                }
            }

        }

    }

    companion object {
        const val EXTRA_ID = "extra_id"
    }
}