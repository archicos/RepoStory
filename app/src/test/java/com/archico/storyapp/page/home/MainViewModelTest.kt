package com.archico.storyapp.page.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.archico.storyapp.adapter.StoryAdapter
import com.archico.storyapp.data.response.Story
import com.archico.storyapp.repository.StoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()
    @Mock
    private lateinit var storyRepository: StoryRepository

    //    TC1: Successful load story
    @Test
    fun `when Get Story Should Not Null and Return Success`() = runTest {
        val dummyData = DataDummy.generateDummy()
        val data: PagingData<Story> = StoryPagingSource.snapshot(dummyData)
        val expected = MutableLiveData<PagingData<Story>>()
        expected.value = data
        Mockito.`when`(storyRepository.getStory()).thenReturn(expected)

        val mainViewModel = MainViewModel(storyRepository)
        val actual: PagingData<Story> = mainViewModel.storyList.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actual)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyData.size, differ.snapshot().size)
        Assert.assertEquals(dummyData[0], differ.snapshot()[0])
    }

    //    TC2: Story is null
    @Test
    fun `when Get Story Empty Should Return No Data`() = runTest {
        val data: PagingData<Story> = PagingData.from(emptyList())
        val expected = MutableLiveData<PagingData<Story>>()
        expected.value = data
        Mockito.`when`(storyRepository.getStory()).thenReturn(expected)
        val mainViewModel = MainViewModel(storyRepository)
        val actual: PagingData<Story> = mainViewModel.storyList.getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actual)
        Assert.assertEquals(0, differ.snapshot().size)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}