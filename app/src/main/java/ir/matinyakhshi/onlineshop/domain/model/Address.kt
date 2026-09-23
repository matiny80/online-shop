data class Address(
    val id: String,
    val recipientName: String,
    val addressText: String,
    val postalCode: String,
    val phoneNumber: String,
    val isDefault: Boolean = false
)