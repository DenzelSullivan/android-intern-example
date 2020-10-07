package com.sullivan.example.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sullivan.example.model.data.Categories
import com.sullivan.example.model.repository.FoodRepository
import com.sullivan.example.model.service.RetrofitBuilder
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {

    // TODO: Remove and use DI framework
    private val foodRepository = FoodRepository(RetrofitBuilder.foodService)

    val categories: MutableLiveData<Categories> = MutableLiveData()

    init {
        viewModelScope.launch {
            categories.postValue(foodRepository.getCategories())
        }
    }
}