package ir.matinyakhshi.onlineshop.data.model

data class UserDto(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val email: String? = null,
    val profileImage: String? = null
)