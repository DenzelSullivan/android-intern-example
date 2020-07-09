package com.sullivan.example.model.service

import com.sullivan.example.model.data.Categories
import com.sullivan.example.model.data.Category
import retrofit2.http.GET

interface FoodService {

    @GET("categories.php")
    suspend fun getCategories() : Categories
}