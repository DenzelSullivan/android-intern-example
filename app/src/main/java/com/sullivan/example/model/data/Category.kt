package com.sullivan.example.model.data

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("idCategory")
    val id: String,
    @SerializedName("strCategory")
    val name: String,
    @SerializedName("strCategoryThumb")
    val imageUrl : String,
    @SerializedName("strCategoryDescription")
    val description: String
)

data class Categories(
    val categories: List<Category>
)
