package ir.matinyakhshi.onlineshop.data.repository

import ir.matinyakhshi.onlineshop.data.local.dao.ProductDao
import ir.matinyakhshi.onlineshop.data.local.entity.ProductEntity
import ir.matinyakhshi.onlineshop.data.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdminRepository @Inject constructor(
    private val productDao: ProductDao
) {

    // دریافت تمامی محصولات از دیتابیس Room و تبدیل Entity به Model اصلی
    val products: Flow<List<Product>> = productDao.getAllProducts().map { entities ->
        entities.map { it.toDomainModel() }
    }

    suspend fun addProduct(product: Product) = withContext(Dispatchers.IO) {
        val newId = if (product.id.isEmpty()) System.currentTimeMillis().toString() else product.id
        val entity = product.copy(id = newId).toEntity()
        productDao.insertOrUpdateProduct(entity)
    }

    suspend fun updateProduct(product: Product) = withContext(Dispatchers.IO) {
        productDao.insertOrUpdateProduct(product.toEntity())
    }

    suspend fun deleteProduct(productId: String) = withContext(Dispatchers.IO) {
        productDao.deleteProductById(productId)
    }

    suspend fun getProductById(productId: String): Product? = withContext(Dispatchers.IO) {
        val numericId = productId.toIntOrNull() ?: return@withContext null
        productDao.getProductById(numericId)?.toDomainModel()
    }

    // توابع کمکی برای تبدیل مدل دیتابیس به مدل برنامه و برعکس
    private fun ProductEntity.toDomainModel() = Product(
        id = id,
        title = title,
        price = price,
        discountPercent = discountPercent,
        stockCount = stockCount,
        category = category,
        description = description,
        imageUrl = imageUrl
    )

    private fun Product.toEntity() = ProductEntity(
        id = id,
        title = title,
        price = price,
        discountPercent = discountPercent,
        stockCount = stockCount,
        category = category,
        description = description,
        imageUrl = imageUrl
    )
}