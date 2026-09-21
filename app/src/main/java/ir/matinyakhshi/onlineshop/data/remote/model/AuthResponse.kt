package ir.matinyakhshi.onlineshop.data.remote.model

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("token") val token: String,
    @SerializedName("user_id") val userId: String
)