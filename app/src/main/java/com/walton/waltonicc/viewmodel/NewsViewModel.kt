package com.walton.waltonicc.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.walton.waltonicc.models.newsmodel.NewsModel
import com.walton.waltonicc.repository.NewsRepository
import com.walton.waltonicc.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository): ViewModel() {

    val newsLiveData: LiveData<NetworkResult<NewsModel>>
    get() = newsRepository.newsLiveData

    fun getNewsData(){
        viewModelScope.launch {
            newsRepository.getNewsData()
        }
    }

}