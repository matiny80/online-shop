package ir.matinyakhshi.onlineshop.data.remote.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("otp_code") val otpCode: String? = null
)