package com.walton.waltonicc.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.walton.waltonicc.api.ICCWorldCupAPI
import com.walton.waltonicc.models.bannermodels.BannerModel
import com.walton.waltonicc.utils.NetworkResult
import javax.inject.Inject

class BannerRepository @Inject constructor(private val ICCWorldCupAPI: ICCWorldCupAPI) {

    private val _userResponseLiveData = MutableLiveData<NetworkResult<BannerModel>>()
    val userResponseLiveData: LiveData<NetworkResult<BannerModel>>
        get() = _userResponseLiveData

    suspend fun getBannerDetails() {
        _userResponseLiveData.postValue(NetworkResult.Loading())
        val response = ICCWorldCupAPI.getBannerDetails()
        Log.d("MainActivity", "getBannerDetails: "+response.body().toString())
        if (response.isSuccessful && response.body() != null) {
            _userResponseLiveData.postValue(NetworkResult.Success(response.body()))
        } else if (response.errorBody() != null) {
            _userResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
        } else {
            _userResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

}