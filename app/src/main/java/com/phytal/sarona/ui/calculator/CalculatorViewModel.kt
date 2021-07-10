package com.phytal.sarona.ui.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "GPA Calculation Coming Soon!"
    }
    val text: LiveData<String> = _text
}