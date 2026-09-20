package ir.matinyakhshi.onlineshop.domain.model

data class User(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val email: String? = null,
    val profileImage: String? = null
)