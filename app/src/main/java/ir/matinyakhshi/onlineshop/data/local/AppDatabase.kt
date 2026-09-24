package ir.matinyakhshi.onlineshop.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.matinyakhshi.onlineshop.data.local.dao.CartDao
import ir.matinyakhshi.onlineshop.data.local.dao.ProductDao
import ir.matinyakhshi.onlineshop.data.local.entity.CartItemEntity
import ir.matinyakhshi.onlineshop.data.local.entity.ProductEntity

@Database(
    entities = [CartItemEntity::class, ProductEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun productDao(): ProductDao
}