package ir.matinyakhshi.onlineshop.data.model

import com.google.gson.annotations.SerializedName

data class StoreConfigDto(
    @SerializedName("store_id") val storeId: String,
    @SerializedName("store_name") val storeName: String,
    @SerializedName("logo_url") val logoUrl: String,
    @SerializedName("primary_color") val primaryColorHex: String,
    @SerializedName("banners") val banners: List<String>
)