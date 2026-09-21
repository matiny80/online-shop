package ir.matinyakhshi.onlineshop.data.remote

import ir.matinyakhshi.onlineshop.data.model.ProductDto
import ir.matinyakhshi.onlineshop.data.model.StoreConfigDto
import ir.matinyakhshi.onlineshop.data.remote.model.AuthResponse
import ir.matinyakhshi.onlineshop.data.remote.model.CreateOrderRequest
import ir.matinyakhshi.onlineshop.data.remote.model.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("api/store/config")
    suspend fun getStoreConfig(
        @Query("store_id") storeId: String
    ): StoreConfigDto

    @GET("api/products")
    suspend fun getProducts(
        @Query("store_id") storeId: String
    ): List<ProductDto>


    @GET("api/products/{id}")
    suspend fun getProductDetail(
        @Path("id") productId: String
    ): ProductDto


    @POST("api/orders")
    suspend fun createOrder(
        @Body request: CreateOrderRequest
    ): Response<Unit>

    @POST("api/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): AuthResponse


}