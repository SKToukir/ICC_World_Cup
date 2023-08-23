package com.walton.waltonicc.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.walton.waltonicc.models.bannermodels.BannerModel
import com.walton.waltonicc.repository.BannerRepository
import com.walton.waltonicc.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BannerViewModel @Inject constructor(private val repository: BannerRepository): ViewModel() {

    val userResponseLiveData : LiveData<NetworkResult<BannerModel>>
    get() = repository.userResponseLiveData

    fun getBannerDetails(){
        viewModelScope.launch {
            repository.getBannerDetails()
        }
    }

}