package com.archico.storyapp.page.home

import com.archico.storyapp.data.response.Story

object DataDummy {

    fun generateDummy(): List<Story> {
        val listStory: MutableList<Story> = arrayListOf()
        for (i in 0..30) {
            val story = Story(
                "https://picsum.photos/300/200?grayscale?blur",
                "2022-02-22T22:22:22Z",
                "Archico Story Eps.$i",
                "Synopsis of Archico Story Vol.$i",
                99.07507F,
                "$i",
                2.975518F,
            )
            listStory.add(story)
        }
        return listStory
    }

}