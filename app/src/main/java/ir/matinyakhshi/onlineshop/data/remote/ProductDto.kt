package com.example.yourapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Double,
    @SerializedName("discount_price") val discountPrice: Double,
    @SerializedName("stock") val stock: Int,
    @SerializedName("images") val images: List<String>
)