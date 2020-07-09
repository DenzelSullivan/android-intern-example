package com.sullivan.example.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sullivan.example.model.data.Categories
import com.sullivan.example.model.repository.FoodRepository
import kotlinx.coroutines.launch

class FoodViewModel(
    private val foodRepository: FoodRepository
) : ViewModel() {

    val categories: MutableLiveData<Categories> = MutableLiveData()

    init {
        viewModelScope.launch {
            categories.postValue(foodRepository.getCategories())
        }
    }
}