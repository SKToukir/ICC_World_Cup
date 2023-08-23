package com.walton.waltonicc.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.walton.waltonicc.api.ICCWorldCupAPI
import com.walton.waltonicc.models.newsmodel.NewsModel
import com.walton.waltonicc.utils.NetworkResult
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsApi: ICCWorldCupAPI) {

    private val _newsResponseLiveData = MutableLiveData<NetworkResult<NewsModel>>()
    val newsLiveData: LiveData<NetworkResult<NewsModel>>
    get() = _newsResponseLiveData

    suspend fun getNewsData(){
        _newsResponseLiveData.postValue(NetworkResult.Loading())
        val response = newsApi.getNewsData()
        if (response.isSuccessful && response.body() != null){
            _newsResponseLiveData.postValue(NetworkResult.Success(response.body()))
        }else if (response.errorBody() != null){
            _newsResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }else{
            _newsResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

}