package com.lyvetech.runorrun.ui.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyvetech.runorrun.db.Run
import com.lyvetech.runorrun.repositories.MainRepository
import com.lyvetech.runorrun.utils.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val runSortedByDate = mainRepository.getAllRunsSortedByDate()
    private val runSortedByDistance = mainRepository.getAllRunsSortedByDistance()
    private val runSortedByTimeInMillis = mainRepository.getAllRunsSortedByTimeInMillis()
    private val runSortedByAverageSpeed = mainRepository.getAllRunsSortedByAverageSpeed()
    private val runSortedByCaloriesBurnt = mainRepository.getAllRunsSortedByCaloriesBurnt()

    val runs = MediatorLiveData<List<Run>>()

    var sortType = SortType.DATE

    init {
        runs.addSource(runSortedByDate) {
            if (sortType == SortType.DATE) {
                it?.let {
                    runs.value = it
                }
            }
        }
        runs.addSource(runSortedByAverageSpeed) {
            if (sortType == SortType.AVG_SPEED) {
                it?.let {
                    runs.value = it
                }
            }
        }
        runs.addSource(runSortedByCaloriesBurnt) {
            if (sortType == SortType.CALORIES_BURNT) {
                it?.let {
                    runs.value = it
                }
            }
        }
        runs.addSource(runSortedByDistance) {
            if (sortType == SortType.DISTANCE) {
                it?.let {
                    runs.value = it
                }
            }
        }
        runs.addSource(runSortedByTimeInMillis) {
            if (sortType == SortType.RUNNING_TIME) {
                it?.let {
                    runs.value = it
                }
            }
        }
    }

    fun sortRuns(sortType: SortType) = when (sortType) {
        SortType.DATE -> runSortedByDate.value?.let {
            runs.value = it
        }
        SortType.RUNNING_TIME -> runSortedByTimeInMillis.value?.let {
            runs.value = it
        }
        SortType.DISTANCE -> runSortedByDistance.value?.let {
            runs.value = it
        }
        SortType.CALORIES_BURNT -> runSortedByCaloriesBurnt.value?.let {
            runs.value = it
        }
        SortType.AVG_SPEED -> runSortedByAverageSpeed.value?.let {
            runs.value = it
        }
    }.also {
        this.sortType = sortType
    }

    fun insertRun(run: Run) = viewModelScope.launch {
        mainRepository.insertRun(run)
    }

}