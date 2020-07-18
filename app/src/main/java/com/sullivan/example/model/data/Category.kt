package com.sullivan.example.model.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    @SerializedName("idCategory")
    val id: String,
    @SerializedName("strCategory")
    val name: String,
    @SerializedName("strCategoryThumb")
    val imageUrl : String,
    @SerializedName("strCategoryDescription")
    val description: String
) : Parcelable

data class Categories(
    val categories: List<Category>
)
