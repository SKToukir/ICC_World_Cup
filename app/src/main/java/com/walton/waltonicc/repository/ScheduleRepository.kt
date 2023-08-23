package com.walton.waltonicc.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.walton.waltonicc.api.ICCWorldCupAPI
import com.walton.waltonicc.models.schedule.ScheduleModel
import com.walton.waltonicc.utils.NetworkResult
import javax.inject.Inject

class ScheduleRepository @Inject constructor(private val iccWorldCupAPI: ICCWorldCupAPI) {

    private val _scheduleResponseLiveData = MutableLiveData<NetworkResult<ScheduleModel>>()
    val scheduleLiveData: LiveData<NetworkResult<ScheduleModel>>
        get() = _scheduleResponseLiveData

    suspend fun getScheduleData() {
        _scheduleResponseLiveData.postValue(NetworkResult.Loading())
        val response = iccWorldCupAPI.getScheduleData()
        if (response.isSuccessful && response.body() != null) {
            _scheduleResponseLiveData.postValue(NetworkResult.Success(response.body()))
        }else if (response.errorBody() != null){
            _scheduleResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }else{
            _scheduleResponseLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

}