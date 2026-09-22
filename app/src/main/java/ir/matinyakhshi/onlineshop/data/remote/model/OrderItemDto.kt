package ir.matinyakhshi.onlineshop.data.remote.model

import com.google.gson.annotations.SerializedName

data class OrderItemDto(
    @SerializedName("product_id") val productId: String,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("price") val price: Double


)

