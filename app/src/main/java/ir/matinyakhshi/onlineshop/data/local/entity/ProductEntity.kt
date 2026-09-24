package ir.matinyakhshi.onlineshop.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val price: Long,
    val discountPercent: Int,
    val stockCount: Int,
    val category: String,
    val description: String,
    val imageUrl: String
)