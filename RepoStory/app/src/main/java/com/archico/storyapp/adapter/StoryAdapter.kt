package com.archico.storyapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.archico.storyapp.databinding.CardViewItemBinding
import com.archico.storyapp.page.detail.DetailStoryActivity
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import com.archico.storyapp.data.response.Story

class StoryAdapter : PagingDataAdapter<Story, StoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = CardViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        if (user != null) {
            holder.bind(user)
        }
    }

    class MyViewHolder(private val binding: CardViewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story){
            binding.tvCardTitle.text = story.name
            binding.tvCardDesc.text = story.description
            Glide.with(binding.root.context)
                .load(story.photoUrl)
                .into(binding.imgCard)

            binding.root.setOnClickListener {
                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        binding.root.context as Activity,
                        Pair(binding.imgCard as View, "profile"),
                        Pair(binding.tvCardTitle as View, "name"),
                        Pair(binding.tvCardDesc as View, "description")
                    )

                val intentDetailActivity = Intent(binding.root.context,DetailStoryActivity::class.java)
                intentDetailActivity.putExtra(DetailStoryActivity.EXTRA_ID,story.id)
                binding.root.context.startActivity(intentDetailActivity,optionsCompat.toBundle())
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }
        }
    }
}