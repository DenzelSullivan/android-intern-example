package com.sullivan.example.model.repository

import com.sullivan.example.model.service.FoodService

class FoodRepository(private val foodService: FoodService) {

    suspend fun getCategories() = foodService.getCategories()
}