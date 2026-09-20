package ir.matinyakhshi.onlineshop.domain.model

data class Product(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val categoryId: String? = null,
    val rating: Double = 0.0,
    val stock: Int = 0
)