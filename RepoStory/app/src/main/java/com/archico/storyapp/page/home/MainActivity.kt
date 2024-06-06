package com.archico.storyapp.page.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager

import com.archico.storyapp.databinding.ActivityMainBinding
import com.archico.storyapp.page.ViewModelFactory
import com.archico.storyapp.page.BaseClass
import com.archico.storyapp.page.add.AddStoryActivity
import com.archico.storyapp.adapter.LoadingStateAdapter
import com.archico.storyapp.adapter.StoryAdapter

class MainActivity : BaseClass() {

    private lateinit var mainBinding: ActivityMainBinding
    private val viewModelFactory = ViewModelFactory.getInstance(this)
    private val mainViewModel by viewModels<MainViewModel> {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        setActionBar()
        getStory()

        val layoutManager = LinearLayoutManager(this)
        mainBinding.rvStory.layoutManager = layoutManager
        mainBinding.fabAddstory.setOnClickListener {
            val addIntent = Intent(this@MainActivity, AddStoryActivity::class.java)
            startActivity(addIntent)
        }
    }

    private fun getStory() {
        val storyAdapter = StoryAdapter()
        mainBinding.rvStory.adapter = storyAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                storyAdapter.retry()
            }
        )
        mainViewModel.storyList.observe(this) {
            mainBinding.shimmerLayout.apply {
                startShimmer()
                visibility = View.GONE
            }
            storyAdapter.submitData(lifecycle, it)
        }
    }
}