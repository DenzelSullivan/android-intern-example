package com.sullivan.example.model.service

import com.sullivan.example.model.data.Categories
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import javax.inject.Singleton

interface FoodService {

    @GET("categories.php")
    suspend fun getCategories(): Categories
}

@Module
@InstallIn(SingletonComponent::class)
object FoodModule {
    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    @Provides
    @Singleton
    fun provideFoodService(
        // Potential dependencies of this type
    ): FoodService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FoodService::class.java)
    }
}