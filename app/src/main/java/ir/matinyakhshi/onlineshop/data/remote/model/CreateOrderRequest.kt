package ir.matinyakhshi.onlineshop.data.remote.model

import com.google.gson.annotations.SerializedName

data class CreateOrderRequest(
    @SerializedName("store_id") val storeId: String,
    @SerializedName("receiver_name") val receiverName: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("address") val address: String,
    @SerializedName("items") val items: List<OrderItemDto>
)