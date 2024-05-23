package com.archico.storyapp.page.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager

import com.archico.storyapp.databinding.ActivityHomeBinding
import com.archico.storyapp.page.ViewModelFactory
import com.archico.storyapp.page.BaseClass
import com.archico.storyapp.page.add.AddStoryActivity
import com.archico.storyapp.adapter.LoadingStateAdapter
import com.archico.storyapp.adapter.StoryAdapter

class HomeActivity : BaseClass() {

    private lateinit var binding: ActivityHomeBinding
    private val factory = ViewModelFactory.getInstance(this)
    private val homeViewModel by viewModels<HomeViewModel> {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBar()

        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager


        binding.fab.setOnClickListener {
            val addIntent = Intent(this@HomeActivity, AddStoryActivity::class.java)
            startActivity(addIntent)
        }

        getData()

    }

    private fun getData() {
        val adapter = StoryAdapter()
        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        homeViewModel.storyList.observe(this) {
            binding.shimmerLayout.apply {
                startShimmer()
                visibility = View.GONE
            }
            adapter.submitData(lifecycle, it)
        }
    }



}