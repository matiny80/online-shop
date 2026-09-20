package ir.matinyakhshi.onlineshop.data.model

data class ProductDto(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val categoryId: String? = null,
    val rating: Double = 0.0,
    val stock: Int = 0
)