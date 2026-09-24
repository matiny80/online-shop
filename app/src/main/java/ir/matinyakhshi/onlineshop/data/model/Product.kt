package ir.matinyakhshi.onlineshop.data.model

data class Product(
        val id: String = "",
        val title: String,
        val price: Long,
        val discountPercent: Int = 0,
        val stockCount: Int,
        val category: String,
        val description: String,
        val imageUrl: String = ""
)