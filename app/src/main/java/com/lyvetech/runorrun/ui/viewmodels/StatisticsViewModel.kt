package com.lyvetech.runorrun.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.lyvetech.runorrun.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    val totalTimeRun = mainRepository.getTotalTimeInMillis()
    val totalDistance = mainRepository.getTotalDistance()
    val totalCaloriesBurnt = mainRepository.getTotalCaloriesBurnt()
    val totalAvgSpeed = mainRepository.getTotalAverageSpeed()

    val runsSortedByDate = mainRepository.getAllRunsSortedByDate()
}