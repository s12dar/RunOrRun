package com.lyvetech.runorrun.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.lyvetech.runorrun.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    val mainRepository: MainRepository
): ViewModel() {

}