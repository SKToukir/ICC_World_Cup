package com.walton.waltonicc.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.walton.waltonicc.models.schedule.ScheduleModel
import com.walton.waltonicc.repository.ScheduleRepository
import com.walton.waltonicc.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(private val scheduleRepository: ScheduleRepository) :
    ViewModel() {

    val scheduleLiveData: LiveData<NetworkResult<ScheduleModel>>
        get() = scheduleRepository.scheduleLiveData

    fun getScheduleData(){
        viewModelScope.launch {
            scheduleRepository.getScheduleData()
        }
    }
}