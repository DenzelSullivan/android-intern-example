package com.sullivan.example.model.repository

import com.sullivan.example.model.service.FoodService
import javax.inject.Inject

class FoodRepository @Inject constructor(
    private val foodService: FoodService
) {

    suspend fun getCategories() = foodService.getCategories()
}