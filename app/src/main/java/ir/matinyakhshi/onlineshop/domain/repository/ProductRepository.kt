package ir.matinyakhshi.onlineshop.domain.repository

import ir.matinyakhshi.onlineshop.domain.model.Product
import ir.matinyakhshi.onlineshop.core.util.Resource

interface ProductRepository {

    suspend fun getProducts(): Resource<List<Product>>

    suspend fun getProductById(
        productId: String
    ): Resource<Product>

    suspend fun getProductsByCategory(
        categoryId: String
    ): Resource<List<Product>>
}