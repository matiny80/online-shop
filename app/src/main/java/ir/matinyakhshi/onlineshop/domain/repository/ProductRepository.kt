package ir.matinyakhshi.onlineshop.domain.repository

import ir.matinyakhshi.onlineshop.domain.model.Product
import kotlin.Result

interface ProductRepository {

    suspend fun getProducts(): Result<List<Product>>

    suspend fun getProductById(
        productId: String
    ): Result<Product>

    suspend fun getProductsByCategory(
        categoryId: String
    ): Result<List<Product>>
}