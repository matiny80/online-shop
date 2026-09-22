package ir.matinyakhshi.onlineshop.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey
    val productId: String,
    val title: String,
    val price: Long,
    val quantity: Int,
    val imageUrl: String? = null
)