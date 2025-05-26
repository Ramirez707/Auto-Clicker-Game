package com.example.autoclicker_game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MyViewModel(private val repository: MyPreferencesRepository) : ViewModel() {
    private val _points = MutableStateFlow(0)
    val points: StateFlow<Int> = _points

    private val _autoClickCount = MutableStateFlow(0)
    val autoClickCount: StateFlow<Int> = _autoClickCount

    init {
        // Load points from repository
        viewModelScope.launch {
            repository.pointsFlow.collectLatest { savedPoints ->
                _points.value = savedPoints
            }
        }
    }

    fun addPoint() {
        _points.value += 1
        savePoints()
    }
    fun resetPoints() {
        _points.value = 0
        _autoClickCount.value = 0
    }

    fun incrementAutoClickCount() {
        _autoClickCount.value += 1
    }
    // Save points asynchronously
    private fun savePoints() {
        viewModelScope.launch {
            repository.savePoints(_points.value)
        }
    }
}