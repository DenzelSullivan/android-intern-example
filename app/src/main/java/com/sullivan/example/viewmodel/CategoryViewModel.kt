package com.sullivan.example.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sullivan.example.model.data.Categories
import com.sullivan.example.model.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : ViewModel() {

    val categories: MutableLiveData<Categories> = MutableLiveData()

    init {
        viewModelScope.launch {
            categories.postValue(foodRepository.getCategories())
        }
    }

    fun getLastIndex(): Int {
        return categories.value?.categories?.lastIndex ?: 0
    }
}

